package pe.com.avivel.sistemas.siva.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    /*private SIPRAOneIPProperties properties;

    @Autowired
    public MailConfig(SIPRAOneIPProperties properties) {
        this.properties = properties;
    }*/

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
/*
        mailSender.setUsername(properties.getMail().getCorreo());
        mailSender.setPassword(properties.getMail().getContrasena());
        mailSender.setHost(properties.getMail().getHost());
        mailSender.setPort(Integer.parseInt(properties.getMail().getPuerto()));
*/
        mailSender.setUsername("notificaciones@avivel.com.pe");
        mailSender.setPassword("TQszw-94WC");
        mailSender.setHost("smtp.office365.com");
        mailSender.setPort(Integer.parseInt("587"));


        Properties props = mailSender.getJavaMailProperties();
/*      props.put("mail.smtp.auth", properties.getMail().isAuth());
        props.put("mail.smtp.starttls.enable", properties.getMail().isStarttls());
        props.put("mail.transport.protocol", properties.getMail().getProtocolo());
*/
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.transport.protocol", "smtp");

        //props.put("mail.smtp.socketFactory.fallback", false);
        //props.put("mail.smtp.ssl.trust", "*");
        //props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        mailSender.setJavaMailProperties(props);

        return mailSender;
    }
}
