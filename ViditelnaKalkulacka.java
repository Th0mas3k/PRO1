import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ViditelnaKalkulacka implements ActionListener {

    JFrame okno;
    JTextField textovePole;
    JButton[] ciselnaTlacitka = new JButton[10];
    JButton[] funkcniTlacitka = new JButton[8];
    JButton tlacitkoPlus, tlacitkoMinus, tlacitkoNasobeni, tlacitkoDeleni;
    JButton tlacitkoTecka, tlacitkoRovnaSe, tlacitkoSmazatVse, tlacitkoSmazatZnak;
    JPanel panel;

    Font mujFont = new Font("Arial", Font.BOLD, 28);
    Font displayFont = new Font("Arial", Font.BOLD, 30);


    private double cislo1 = 0;
    private char aktualniOperator = ' ';


    private boolean poVypoctu = false;
    private boolean posledniBylOperator = false;


    public ViditelnaKalkulacka() {
        okno = new JFrame("Kalkulačka s viditelným příkladem");
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        okno.setSize(420, 580);
        okno.setLayout(null);

        textovePole = new JTextField();
        textovePole.setBounds(50, 25, 300, 60);
        textovePole.setFont(displayFont);
        textovePole.setEditable(false);
        textovePole.setHorizontalAlignment(JTextField.RIGHT);

        tlacitkoPlus = new JButton("+");
        tlacitkoMinus = new JButton("-");
        tlacitkoNasobeni = new JButton("*");
        tlacitkoDeleni = new JButton("/");
        tlacitkoTecka = new JButton(".");
        tlacitkoRovnaSe = new JButton("=");
        tlacitkoSmazatVse = new JButton("C");
        tlacitkoSmazatZnak = new JButton("DEL");

        funkcniTlacitka[0] = tlacitkoPlus;
        funkcniTlacitka[1] = tlacitkoMinus;
        funkcniTlacitka[2] = tlacitkoNasobeni;
        funkcniTlacitka[3] = tlacitkoDeleni;
        funkcniTlacitka[4] = tlacitkoTecka;
        funkcniTlacitka[5] = tlacitkoRovnaSe;
        funkcniTlacitka[6] = tlacitkoSmazatVse;
        funkcniTlacitka[7] = tlacitkoSmazatZnak;

        for (int i = 0; i < 8; i++) {
            funkcniTlacitka[i].addActionListener(this);
            funkcniTlacitka[i].setFont(mujFont);
            funkcniTlacitka[i].setFocusable(false);
        }

        for (int i = 0; i < 10; i++) {
            ciselnaTlacitka[i] = new JButton(String.valueOf(i));
            ciselnaTlacitka[i].addActionListener(this);
            ciselnaTlacitka[i].setFont(mujFont);
            ciselnaTlacitka[i].setFocusable(false);
        }

        tlacitkoSmazatZnak.setBounds(50, 450, 145, 50);
        tlacitkoSmazatVse.setBounds(205, 450, 145, 50);

        panel = new JPanel();
        panel.setBounds(50, 100, 300, 330);
        panel.setLayout(new GridLayout(4, 4, 10, 10));

        panel.add(ciselnaTlacitka[1]);
        panel.add(ciselnaTlacitka[2]);
        panel.add(ciselnaTlacitka[3]);
        panel.add(tlacitkoPlus);
        panel.add(ciselnaTlacitka[4]);
        panel.add(ciselnaTlacitka[5]);
        panel.add(ciselnaTlacitka[6]);
        panel.add(tlacitkoMinus);
        panel.add(ciselnaTlacitka[7]);
        panel.add(ciselnaTlacitka[8]);
        panel.add(ciselnaTlacitka[9]);
        panel.add(tlacitkoNasobeni);
        panel.add(tlacitkoTecka);
        panel.add(ciselnaTlacitka[0]);
        panel.add(tlacitkoRovnaSe);
        panel.add(tlacitkoDeleni);

        okno.add(panel);
        okno.add(tlacitkoSmazatZnak);
        okno.add(tlacitkoSmazatVse);
        okno.add(textovePole);
        okno.setVisible(true);
    }

    public static void main(String[] args) {
        new ViditelnaKalkulacka();
    }


    private String formatovatCislo(double cislo) {
        if (cislo == (long) cislo) {
            return String.format("%d", (long) cislo);
        } else {
            return String.format("%s", cislo);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String aktualniTextPole = textovePole.getText();

        for (int i = 0; i < 10; i++) {
            if (e.getSource() == ciselnaTlacitka[i]) {
                if (poVypoctu) {
                    textovePole.setText(String.valueOf(i));
                    poVypoctu = false;
                } else {
                    textovePole.setText(aktualniTextPole.concat(String.valueOf(i)));
                }
                posledniBylOperator = false;
                return;
            }
        }


        if (e.getSource() == tlacitkoTecka) {
            if (poVypoctu) {
                textovePole.setText("0.");
                poVypoctu = false;
                posledniBylOperator = false;
                return;
            }
            if (posledniBylOperator) {
                textovePole.setText(aktualniTextPole.concat("0."));
            } else {
                String segment = aktualniTextPole;
                if (aktualniOperator != ' ' && aktualniTextPole.contains(" " + aktualniOperator + " ")) {
                    int indexOperatoru = aktualniTextPole.lastIndexOf(" " + aktualniOperator + " ");
                    segment = aktualniTextPole.substring(indexOperatoru + 3);
                }
                if (!segment.contains(".")) {
                    textovePole.setText(aktualniTextPole.concat("."));
                }
            }
            posledniBylOperator = false;
            return;
        }

        if (e.getSource() == tlacitkoSmazatVse) {
            textovePole.setText("");
            cislo1 = 0;
            aktualniOperator = ' ';
            poVypoctu = false;
            posledniBylOperator = false;
            return;
        }

        if (e.getSource() == tlacitkoSmazatZnak) {
            if (!aktualniTextPole.isEmpty()) {
                String novyText = aktualniTextPole.substring(0, aktualniTextPole.length() - 1);
                if (novyText.endsWith(" ") && (novyText.endsWith(" + ") || novyText.endsWith(" - ") || novyText.endsWith(" * ") || novyText.endsWith(" / "))) {
                    novyText = novyText.substring(0, novyText.length() - 3);
                    posledniBylOperator = false;
                    if (novyText.contains(" + ") || novyText.contains(" - ") || novyText.contains(" * ") || novyText.contains(" / ")) {
                    } else {
                        aktualniOperator = ' ';
                    }

                } else if (novyText.isEmpty() || Character.isDigit(novyText.charAt(novyText.length()-1)) || novyText.endsWith(".")) {
                    posledniBylOperator = false;
                } else {
                    posledniBylOperator = true;
                }
                textovePole.setText(novyText);
                poVypoctu = false;
            }
            return;
        }

        if (e.getSource() == tlacitkoPlus || e.getSource() == tlacitkoMinus ||
                e.getSource() == tlacitkoNasobeni || e.getSource() == tlacitkoDeleni) {

            char novyOperator = ' ';
            if (e.getSource() == tlacitkoPlus) novyOperator = '+';
            else if (e.getSource() == tlacitkoMinus) novyOperator = '-';
            else if (e.getSource() == tlacitkoNasobeni) novyOperator = '*';
            else if (e.getSource() == tlacitkoDeleni) novyOperator = '/';

            if (aktualniTextPole.isEmpty() && novyOperator == '-') {
                textovePole.setText("-");
                posledniBylOperator = false;
                return;
            }

            if (aktualniTextPole.isEmpty() || (aktualniTextPole.equals("-") && novyOperator != '-')) {
                return;
            }


            if (posledniBylOperator) {
                String textBezPoslednihoOp = aktualniTextPole.substring(0, aktualniTextPole.length() - 3);
                textovePole.setText(textBezPoslednihoOp + " " + novyOperator + " ");
            } else {
                if (aktualniOperator == ' ') {
                    try {
                        cislo1 = Double.parseDouble(aktualniTextPole);
                        textovePole.setText(aktualniTextPole + " " + novyOperator + " ");
                    } catch (NumberFormatException ex) {
                        System.err.println("Chyba parsování pro cislo1: " + aktualniTextPole);
                        return;
                    }
                } else {
                    int indexOperatoru = aktualniTextPole.lastIndexOf(" " + aktualniOperator + " ");
                    if (indexOperatoru == -1) {
                        System.err.println("Chybějící předchozí operátor pro řetězení.");
                        textovePole.setText("Chyba");
                        return;
                    }
                    String textDruhehoCisla = aktualniTextPole.substring(indexOperatoru + 3); // +3 za " o "
                    try {
                        double cislo2 = Double.parseDouble(textDruhehoCisla);
                        double vysledek = spocitej(cislo1, cislo2, aktualniOperator);
                        if (Double.isNaN(vysledek)) {
                            textovePole.setText("Chyba: Dělení nulou!");
                            resetKalkulackyPoChybe();
                            return;
                        }
                        cislo1 = vysledek;
                        textovePole.setText(formatovatCislo(cislo1) + " " + novyOperator + " ");
                    } catch (NumberFormatException ex) {
                        textovePole.setText("Chyba formátu");
                        resetKalkulackyPoChybe();
                        return;
                    }
                }
            }
            aktualniOperator = novyOperator;
            poVypoctu = false;
            posledniBylOperator = true;
            return;
        }


        if (e.getSource() == tlacitkoRovnaSe) {
            if (aktualniTextPole.isEmpty() || aktualniOperator == ' ' || posledniBylOperator) {
                return;
            }

            int indexOperatoru = aktualniTextPole.lastIndexOf(" " + aktualniOperator + " ");
            if (indexOperatoru == -1 || (indexOperatoru + 3) >= aktualniTextPole.length() ) {
                System.err.println("Chyba: chybí druhé číslo nebo operátor pro výpočet.");
                textovePole.setText("Chyba syntaxe");
                resetKalkulackyPoChybe();
                return;
            }

            String textDruhehoCisla = aktualniTextPole.substring(indexOperatoru + 3); // +3 za " o "
            try {
                double cislo2 = Double.parseDouble(textDruhehoCisla);
                double vysledek = spocitej(cislo1, cislo2, aktualniOperator);

                if (Double.isNaN(vysledek)) {
                    textovePole.setText("Chyba: Dělení nulou!");
                    resetKalkulackyPoChybe();
                } else {
                    textovePole.setText(formatovatCislo(vysledek));
                    cislo1 = vysledek;
                    aktualniOperator = ' ';
                    poVypoctu = true;
                    posledniBylOperator = false;
                }
            } catch (NumberFormatException ex) {
                textovePole.setText("Chyba formátu");
                resetKalkulackyPoChybe();
            }
        }
    }

    private void resetKalkulackyPoChybe() {
        cislo1 = 0;
        aktualniOperator = ' ';
        poVypoctu = true;
        posledniBylOperator = false;
    }


    private double spocitej(double c1, double c2, char op) {
        switch (op) {
            case '+': return c1 + c2;
            case '-': return c1 - c2;
            case '*': return c1 * c2;
            case '/':
                if (c2 == 0) return Double.NaN;
                return c1 / c2;
            default: return 0;
        }
    }
}