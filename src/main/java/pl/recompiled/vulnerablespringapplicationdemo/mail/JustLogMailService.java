package pl.recompiled.vulnerablespringapplicationdemo.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
class JustLogMailService implements MailService {

    @Override
    public void sendActivationMail(String token) {
        log.info("Sent activation mail with token: " + token);
    }
}
