package com.example.envio_Mails;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("enviar-email")
public class EnvioEmailView extends VerticalLayout {

    private EmailField emailDestino;
    private TextField assunto;
    private TextArea mensagem;
    private Button enviarButton;

    public EnvioEmailView() {
        setAlignItems(Alignment.CENTER);
        setSpacing(true);

        emailDestino = new EmailField("Destinatário");
        emailDestino.setPlaceholder("exemplo@gmail.com");
        emailDestino.setWidth("400px");

        assunto = new TextField("Assunto");
        assunto.setWidth("400px");

        mensagem = new TextArea("Mensagem");
        mensagem.setWidth("400px");
        mensagem.setHeight("200px");

        enviarButton = new Button("Enviar Email", e -> enviarEmailSimulado());

        add(emailDestino, assunto, mensagem, enviarButton);
    }

    private void enviarEmailSimulado() {
        String to = emailDestino.getValue();
        String subject = assunto.getValue();
        String body = mensagem.getValue();

        // Simulação: nunca falha
        Notification.show("Email enviado com sucesso para: " + to, 3000, Notification.Position.MIDDLE);

        // Opcional: pode imprimir no console para debugging
        System.out.println("=== Email Simulado ===");
        System.out.println("Para: " + to);
        System.out.println("Assunto: " + subject);
        System.out.println("Mensagem: " + body);
        System.out.println("=====================");
    }
}
