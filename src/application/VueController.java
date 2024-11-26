package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class VueController {

  @FXML
  private Button calculate;

  @FXML
  private Label totalTip;

  @FXML
  private TextField date;

  @FXML
  private Label totalAmount;

  @FXML
  private Label error;  

  @FXML
  private TextField billAmount;

  @FXML
  private TextField tipPercentage;

  @FXML
  private TextField numberOfPeople;

  public void calculate(ActionEvent e) {
    error.setText("");  

    try {    
      double bill = parseDoubleField(billAmount.getText(), "Montant de la facture");
      double tip = parseDoubleField(tipPercentage.getText(), "Pourcentage du pourboire");
      int people = parseIntField(numberOfPeople.getText(), "Nombre de personnes");
      String inputDate = date.getText();

      TipCalculator calculator = new TipCalculator(bill, tip, people, inputDate);

      double tipPerPerson = calculator.calculateTipPerPerson();
      double totalPerPerson = calculator.calculateTotalPerPerson();
      calculator.writeFile();
      totalTip.setText(String.format("%.2f", tipPerPerson));
      totalAmount.setText(String.format("%.2f", totalPerPerson));

    } catch (IllegalArgumentException e1) {
      error.setText(e1.getMessage());
    } catch (Exception e1) {
      error.setText("Erreur inattendue : " + e1.getMessage());
    }
  }

  private double parseDoubleField(String value, String fieldName) {
    try {
      return Double.parseDouble(value);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(fieldName + " doit être une valeur numérique valide.");
    }
  }

  private int parseIntField(String value, String fieldName) {
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(fieldName + " doit être un nombre entier valide.");
    }
  }
}
