import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {
    public static void Start() {
        System.out.println("N10 G21 G40 G49 G54 G80 G90");
        System.out.println("N20 M06 T01 (FREZA D1)");
        System.out.println("N30 G43 H01");
        System.out.println("N40 M03 S1000");
    }

    public static void End(int counterN) {
        System.out.println("N" + counterN + " G01 Z5");
        counterN += 10;
        System.out.println("N" + counterN + " M05");
        counterN += 10;
        System.out.println("N" + counterN + " M30");
    }

    public static void IJ(int counterInput, ArrayList<Double> anchorPointsX, ArrayList<Double> anchorPointsY, ArrayList<Integer> sep, ArrayList<Double> forI, ArrayList<Double> forJ) {

        //System.out.println(counterInput);
        //System.out.println(length_square);
        double signY = 0;
        double signX = 0;
        double length_square = 0;
        //координаты серединной точки
        double coordX = 0;
        double coordY = 0;
        for (int i = 0; i < anchorPointsX.size(); i++) {
            if (i == anchorPointsX.size() - 1) {
                signX = anchorPointsX.get(i) - anchorPointsX.get(0);
                signY = anchorPointsY.get(i) - anchorPointsY.get(0);
                if (signY < 0) {
                    if (signX > 0) {
                        coordX = anchorPointsX.get(0) + length_square;
                        coordY = anchorPointsY.get(i) + length_square;
                    } else if (signX < 0) {
                        coordX = anchorPointsX.get(i) + length_square;
                        coordY = anchorPointsY.get(0) - length_square;
                    }
                } else if (signY > 0) {
                    if (signX > 0) {
                        coordX = anchorPointsX.get(i) - length_square;
                        coordY = anchorPointsY.get(0) + length_square;
                    } else if (signX < 0) {
                        coordX = anchorPointsX.get(0) - length_square;
                        coordY = anchorPointsY.get(i) - length_square;
                    }
                }
                double I = coordX - anchorPointsX.get(i);
                double J = coordY - anchorPointsY.get(i);
                forI.add(I);
                forJ.add(J);
                //System.out.println(coordX + " !!! " + coordY);
                break;
            }
            for (int j = 0; j < sep.size(); j++) {
                if (i == sep.get(j)) {
                    length_square = Math.abs(anchorPointsX.get(i) - anchorPointsX.get(i + 1));
                    //System.out.println(length_square);
                    signX = anchorPointsX.get(i) - anchorPointsX.get(i + 1);
                    signY = anchorPointsY.get(i) - anchorPointsY.get(i + 1);
                    if (signY < 0) {
                        if (signX > 0) {
                            coordX = anchorPointsX.get(i + 1) + length_square;
                            coordY = anchorPointsY.get(i) + length_square;
                        } else if (signX < 0) {
                            coordX = anchorPointsX.get(i) + length_square;
                            coordY = anchorPointsY.get(i + 1) - length_square;
                        }
                    } else if (signY > 0) {
                        if (signX > 0) {
                            coordX = anchorPointsX.get(i) - length_square;
                            coordY = anchorPointsY.get(i + 1) + length_square;
                        } else if (signX < 0) {
                            coordX = anchorPointsX.get(i + 1) - length_square;
                            coordY = anchorPointsY.get(i) - length_square;
                        }
                    }
                    //System.out.println(coordX + " !!! " + coordY);
                    double I = coordX - anchorPointsX.get(i);
                    double J = coordY - anchorPointsY.get(i);
                    forI.add(I);
                    forJ.add(J);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        int counterN = 50;
        String current_command = "";
        int counterInputString = -1;
        ArrayList<Double> anchorPointsX = new ArrayList<>();
        ArrayList<Double> anchorPointsY = new ArrayList<>();
        ArrayList<Double> forI = new ArrayList<>();
        ArrayList<Double> forJ = new ArrayList<>();
        ArrayList<Integer> sep = new ArrayList<>();
        String coordinates = "";
        System.out.println("Введите опроные точки. В конце ввода введите end");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int space = 0;
        int separator = 0;
        boolean Space = false;
        boolean Separator = false;
        while (true) {
            coordinates = reader.readLine();
            if (coordinates.equals("end")) {
                IJ(counterInputString, anchorPointsX, anchorPointsY, sep, forI, forJ);
                break;
            } else {
                counterInputString++;
                for (int i = 0; i < coordinates.length(); i++) {
                    if (coordinates.charAt(i) == ' ') {
                        //выводится прямая
                        space = i;
                        Space = true;
                    } else if (coordinates.charAt(i) == '|') {
                        //выодится окружность
                        separator = i;
                        Separator = true;
                    }
                }
                if (Space) {
                    //текущий вывод - прямая
                    String temp = "";
                    int temp_i = 0;
                    for (int i = 0; i < space; i++) {
                        temp += coordinates.charAt(i);
                    }
                    anchorPointsX.add(Double.parseDouble(temp));
                    temp = "";
                    for (int i = space + 1; i < coordinates.length(); i++) {
                        temp += coordinates.charAt(i);
                    }
                    anchorPointsY.add(Double.parseDouble(temp));
                    temp = "";
                    Space = false;
                } else if (Separator) {
                    //текущий вывод - окружность
                    String temp = "";
                    for (int i = 0; i < separator; i++) {
                        temp += coordinates.charAt(i);
                    }
                    anchorPointsX.add(Double.parseDouble(temp));
                    temp = "";
                    for (int i = separator + 1; i < coordinates.length(); i++) {
                        temp += coordinates.charAt(i);
                    }
                    anchorPointsY.add(Double.parseDouble(temp));
                    temp = "";
                    Separator = false;
                    sep.add(counterInputString);

                }

            }

        }
//        for (int i = 0; i < anchorPointsX.size(); i++) {
//            System.out.print(anchorPointsX.get(i) + " ");
//        }
//        System.out.println();
//        for (int i = 0; i < anchorPointsY.size(); i++) {
//            System.out.print(anchorPointsY.get(i) + " ");
//        }
//        System.out.println();
//        for (int i = 0; i < forI.size(); i++) {
//            System.out.print(forI.get(i) + " ");
//        }
//        System.out.println();
//        for (int i = 0; i < forJ.size(); i++) {
//            System.out.print(forJ.get(i) + " ");
//        }
//        System.out.println();
//        for (int i = 0; i < sep.size(); i++) {
//            System.out.println(sep.get(i));
//        }
        Start();
        //counterN+=10;
        System.out.println("N" + counterN + " G00" + " X" + anchorPointsX.get(0) + " Y" + anchorPointsY.get(0));
        counterN+=10;
        System.out.println("N" + counterN + " G00 Z0.5");
        counterN+=10;
        System.out.println("N" + counterN + " G01 Z-1 F25");
        counterN+=10;
        int counterIJ = 0;
        boolean flag2 = false;
        for(int i = 0; i < anchorPointsX.size(); i++) {
            boolean flag = false;

            //System.out.print(i + "I");
            //System.out.println("I=" + i);

            // System.out.println();
            for(int j = 0; j < sep.size(); j++) {
               // System.out.println("J=" + j);
                if(i == anchorPointsX.size() - 1) {
                     //System.out.println("The last point");//это последняя точка
                    if(i == sep.get(j)) {
                        //значит эта точка начало окружности
                        System.out.println("N" + counterN + " G02" + " X" + anchorPointsX.get(0) + " Y" + anchorPointsY.get(0) + " I" + forI.get(counterIJ) + " J" + forJ.get(counterIJ));
                        counterIJ++;
                        counterN +=10;
                        flag2 = true;
                        break;
                    }

                }
                //System.out.println(j + "J");
                if(i == sep.get(j)) {
                    if(i== 0) {
                       // System.out.println("Первая точка окружность");
                    }
                    else {
                       // System.out.println("Не первая точка, но окружность");
                    }

                    System.out.println("N" + counterN + " G02" + " X" + anchorPointsX.get(i+1) + " Y" + anchorPointsY.get(i+1) + " I" + forI.get(counterIJ) + " J" + forJ.get(counterIJ));
                    counterIJ++;
                    counterN+=10;
                    flag = true;
                    break;

                }


            }
            if(!flag && i != anchorPointsX.size() - 1) {
                //System.out.println("прямая");
                System.out.println("N" + counterN + " G01" + " X" + anchorPointsX.get(i+1) + " Y" + anchorPointsY.get(i+1));
                counterN+=10;
            }

        }
        if(!flag2) {
            System.out.println("N" + counterN + " G01" + " X" + anchorPointsX.get(0) + " Y" + anchorPointsY.get(0));
            counterN+=10;
        }
        End(counterN);
    }
}
