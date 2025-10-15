package com.example.feature_conversaoDeMoeda;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

@PageTitle("Conversão de Moedas")
@Route("currency") // URL da página: http://localhost:8080/currency
public class CurrencyView extends VerticalLayout {

    private final OkHttpClient client = new OkHttpClient();

    public CurrencyView() {
        setSpacing(true);
        setPadding(true);
        setAlignItems(Alignment.CENTER);

        H1 title = new H1("Conversor de Moedas 💱");

        TextField fromCurrency = new TextField("De (ex: EUR)");
        TextField toCurrency = new TextField("Para (ex: USD)");
        NumberField amount = new NumberField("Valor a converter");
        amount.setValue(1.0);

        Button convertButton = new Button("Converter", event -> {
            String from = fromCurrency.getValue().trim().toUpperCase();
            String to = toCurrency.getValue().trim().toUpperCase();
            Double value = amount.getValue();

            if (from.isEmpty() || to.isEmpty() || value == null) {
                Notification.show("Preenche todos os campos!", 3000, Notification.Position.MIDDLE);
                return;
            }

            try {
                double converted = convertCurrency(from, to, value);
                Notification.show(String.format("%.2f %s = %.2f %s", value, from, converted, to),
                        5000, Notification.Position.MIDDLE);
            } catch (Exception e) {
                Notification.show("Erro ao converter: " + e.getMessage(), 5000, Notification.Position.MIDDLE);
            }
        });

        add(title, fromCurrency, toCurrency, amount, convertButton);
    }

    /**
     * Faz o pedido HTTP à API de câmbios e devolve o valor convertido
     */
    private double convertCurrency(String from, String to, double amount) throws Exception {
        String url = String.format("https://api.frankfurter.app/latest?amount=%.2f&from=%s&to=%s",
                amount, from, to);

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new Exception("Erro na resposta da API");
            }

            String jsonResponse = response.body().string();
            JSONObject json = new JSONObject(jsonResponse);
            System.out.println(json.toString(2)); // imprime o JSON recebido

            if (!json.has("rates")) {
                throw new Exception("Formato inesperado de resposta da API");
            }

            JSONObject rates = json.getJSONObject("rates");
            if (!rates.has(to)) {
                throw new Exception("Moeda de destino não encontrada na resposta da API");
            }

            return rates.getDouble(to);
        }
    }

}
