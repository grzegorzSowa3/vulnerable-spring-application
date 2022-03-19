package pl.recompiled.vulnerablespringapplicationdemo.mail;

public interface MailService {

    void sendActivationMail(String token);
}
