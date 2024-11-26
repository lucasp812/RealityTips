package application;

import java.util.regex.Matcher;
import java.io.*; 
import  java.util.regex.Pattern;
import java.util.*;




public class TipCalculator implements Serializable {

  
  private static final long serialVersionUID = 1L;  

  private double billAmount;
  private double tipPercentage;
  private int numberOfPeople;

  private String date;



  public double getTipPercentage() {
    return tipPercentage;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public double getBillAmount() {
    return billAmount;
  }

  public void setBillAmount(double billAmount) {
    this.billAmount = billAmount;
  }

  public void setTipPercentage(double tipPercentage) {
    this.tipPercentage = tipPercentage;
  }

  public int getNumberOfPeople() {
    return numberOfPeople;
  }

  public void setNumberOfPeople(int numberOfPeople) {
    this.numberOfPeople = numberOfPeople;
  }
  

  public TipCalculator(double billAmount, double tipPercentage, int numberOfPeople, String date) {

 
    validatePositiveValue(billAmount, "Le bill ne peut pas être négatif.");
    validatePositiveValue(tipPercentage, "Le tip ne peut pas être négatif.");
    validatePositiveValue(numberOfPeople, "Le nombre de personnes doit être supérieur à zéro.");
    validateIsNumeric(billAmount,"Test");
    validateIsNumeric(tipPercentage,"Test1");
    validateIsNumeric(numberOfPeople,"Test2");
    validateDate(date);
  
    this.billAmount = billAmount;
    this.tipPercentage = tipPercentage;
    this.numberOfPeople = numberOfPeople;
    this.date = date;
  }

  public double calculateTipPerPerson() {
    double totalTip = billAmount * tipPercentage / 100;
    return totalTip / numberOfPeople;
  }


  private void validateIsNumeric(double value, String errorMessage) {
    if (Double.isNaN(value)) {
        throw new NumberFormatException(errorMessage);
    }
}
/*
private void validateIsNumeric(int value, String errorMessage) {
    if (value < 0) {  
        throw new  NumberFormatException(errorMessage);
    }
}*/


  private void validatePositiveValue(double value, String errorMessage) {
    if (value < 0) {
      throw new IllegalArgumentException(errorMessage);
    }
  }

  private void validatePositiveValue(int value, String errorMessage) {
    if (value <= 0) { 
      throw new IllegalArgumentException(errorMessage);
    }
  }

  public double calculateTotalPerPerson() {
    double totalTip = billAmount * tipPercentage / 100;
    double totalAmount = billAmount + totalTip;
    return totalAmount / numberOfPeople;

  }

  private void validateDate(String date) {
    String regex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(\\d{4})$"; 
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(date);
    if (!matcher.matches()) {
      throw new IllegalArgumentException("La date du calcul doit être au format dd/mm/yyyy.");
    }
  }


  public void writeFile() {
    
    File fichier = new File("RealityTips.txt");
    List<String> lines = new ArrayList<>();
    boolean dateFound = false;
    try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ; ");
            if (parts.length == 4 && parts[0].equals(this.date)) {   
                lines.add(this.date + " ; " + this.billAmount + " ; " + this.tipPercentage + " ; " + this.numberOfPeople);
                dateFound = true;
            } else {
                lines.add(line);  
            }
        }
    } catch (IOException e) {
        System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
    }
    if (!dateFound) {
        lines.add(this.date + " ; " + this.billAmount + " ; " + this.tipPercentage + " ; " + this.numberOfPeople);
    }
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichier))) {
        for (String line : lines) {
            writer.write(line);
            writer.newLine();
        }
        System.out.println("Données enregistrées avec succès dans RealityTips.txt");
    } catch (IOException e) {
        System.err.println("Erreur lors de l'écriture dans le fichier : " + e.getMessage());
    }
}
}