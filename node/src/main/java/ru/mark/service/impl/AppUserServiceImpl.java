package ru.mark.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.mark.dao.AppUserDAO;
import ru.mark.dto.MailParams;
import ru.mark.entity.AppUser;
import ru.mark.entity.enums.UserState;
import ru.mark.service.AppUserService;
import ru.mark.util.CryptoTool;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import static ru.mark.entity.enums.UserState.WAIT_FOR_EMAIL_STATE;

@Service
@Log4j
public class AppUserServiceImpl implements AppUserService {
    private final AppUserDAO appUserDAO;
    private final CryptoTool cryptoTool;
    @Value("${service.mail.uri}")
    private String mailServiceUri;

    public AppUserServiceImpl(AppUserDAO appUserDAO, CryptoTool cryptoTool) {
        this.appUserDAO = appUserDAO;
        this.cryptoTool = cryptoTool;
    }

    @Override
    public String registerUser(AppUser appUser) {
        if(appUser.getIsActive()) return "Вы уже зарегестрированы";
        else if(appUser.getEmail() != null){
            return "Вам на почту уже отправлено письмо. "
                        + "Перейдите по ссылке в письме для подтверждения регисрации.";
        }
        appUser.setUserState(WAIT_FOR_EMAIL_STATE);
        appUserDAO.save(appUser);
        return "Введите, пожалуйста, ваш email:";
    }

    @Override
    public String setEmail(AppUser appUser, String email) {
        try{
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException exception){
            return "Введите, пожалуйста, корректный email. Для отметны команды введите /cancel";
        }
        var optional = appUserDAO.findByEmail(email);
        if(optional.isEmpty()){
            appUser.setEmail(email);
            appUser.setUserState(UserState.BASIC_STATE);
            appUserDAO.save(appUser);

            var cryptoUserId = cryptoTool.hashOf(appUser.getId());
            var response = sendRequestToMailService(cryptoUserId, email);
            if(response.getStatusCode() != HttpStatus.OK){
                var msg = String.format("Отправка электронного письма на почту %s не удалась.", email);
                log.error(msg);
                appUser.setEmail(null);
                appUserDAO.save(appUser);
                return msg;
            }
            return "Вам на почту было отправлено письмо."
                        + "Перейдите по ссылке в письме для подтверждения регистрации.";
        } else {
            return "Этот email уже используется. Введите корректрый email."
                        + "Для отмены комманды введите /cancel";
        }
    }

    private ResponseEntity<?> sendRequestToMailService(String cryptoUserId, String email) {
        var restTemplate = new RestTemplate();
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var mailParams = MailParams.builder()
                .id(cryptoUserId)
                .mailTo(email)
                .build();
        var request = new HttpEntity<MailParams>(mailParams, headers);
        return restTemplate.exchange(mailServiceUri,
                HttpMethod.POST,
                request,
                String.class);
    }
}
