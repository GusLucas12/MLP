/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mlp;

/**
 *
 * @author gusta
 */
public class MLPMain {

    private static double[][][] baseE = new double[][][]{
        {{0, 0}, {0}},
        {{0, 1}, {0}},
        {{1, 0}, {0}},
        {{1, 1}, {1}}
    };
    private static double[][][] baseOu = new double[][][]{
        {{0, 0}, {0}},
        {{0, 1}, {1}},
        {{1, 0}, {1}},
        {{1, 1}, {1}}
    };
    private static double[][][] baseXOr = new double[][][]{
        {{0, 0}, {0}},
        {{0, 1}, {1}},
        {{1, 0}, {1}},
        {{1, 1}, {0}}
    };
    private static double[][][] baseRobo = new double[][][]{
        {{0, 0, 0}, {1, 1}},
        {{0, 0, 1}, {0, 1}},
        {{0, 1, 0}, {0, 1}},
        {{0, 1, 1}, {0, 1}},
        {{1, 0, 0}, {1, 1}},
        {{1, 0, 1}, {1, 1}},
        {{1, 1, 0}, {1, 1}},
        {{1, 1, 1}, {0, 1}}
    };
    // Conjunto de treinamento (70%)
    private static double[][][] baseTreinamento = {
        {{1, 1, 1, 1}, {1}},
        {{1, 1, 1, 0}, {1}},
        {{1, 1, 0, 1}, {1}},
        {{1, 1, 0, 0}, {1}},
        {{1, 0, 1, 1}, {1}},
        {{1, 0, 1, 0}, {0}},
        {{1, 0, 0, 1}, {0}},
        {{1, 0, 0, 0}, {0}},
        {{0, 1, 1, 1}, {1}},
        {{0, 1, 1, 0}, {0}},
        {{0, 1, 0, 1}, {0}}
    };

// Conjunto de teste (30%)
    private static double[][][] baseTeste = {
        {{0, 1, 0, 0}, {0}},
        {{0, 0, 1, 1}, {1}},
        {{0, 0, 1, 0}, {0}},
        {{0, 0, 0, 1}, {0}},
        {{0, 0, 0, 0}, {0}}
    };

    public static void main(String[] args) {
        // Defina os parâmetros do MLP, como número de neurônios na camada de entrada, camada oculta e saída
        int qtdIn = 4;     // Número de entradas (depende da base de dados)
        // Número de neurônios na camada oculta (hiperparâmetro)
        int qtdOut = 1;    // Número de saídas
        int qtdH = 6;
        double ni = 0.000001; // Taxa de aprendizado

        MLP mlp = new MLP(qtdIn, qtdH, qtdOut, ni);

        // Número de épocas para o treinamento
        for (int e = 0; e < 1000000; e++) {
            double erroApTreino = 0;
            double erroOTreino = 0;
            double erroApTeste = 0;
            double erroOTeste = 0;

//            for (int a = 0; a < baseTreinamento.length; a++) {
//                double[] x = baseTreinamento[a][0]; // Entradas
//                double[] y = baseTreinamento[a][1]; // Saída esperada
//                // Chama o método `treinar` do MLP e obtém a saída para essa entrada
//                double[] out = mlp.treinar(x, y);
//                for (int j = 0; j < y.length; j++) {
//                    erroApTreino += Math.abs(y[j] - out[j]);
//                }
//                double[] outLinha = thresHold(out);
//                for (int j = 0; j < y.length; j++) {
//                    double soma = Math.abs(y[j] - outLinha[j]);
//
//                    if (soma > 0) {
//                        erroOTreino += 1;
//                    } else {
//                        erroOTreino += 0;
//                    }
//
//                }
//                // Imprime o erro para cada época
//            }
//            for (int a1 = 0; a1 < baseTeste.length; a1++) {
//               double[] x1 = baseTeste[a1][0]; // Entradas
//                double[] y1 = baseTeste[a1][1]; // Saída esperada
//                // Chama o método `testar` do MLP e obtém a saída para essa entrada
//                double[] out1 = mlp.testar(x1, y1);
//                for (int j = 0; j < y1.length; j++) {
//                    erroApTeste += Math.abs(y1[j] - out1[j]);
//                }
//                double[] outLinha = thresHold(out1);
//                for (int j = 0; j < y1.length; j++) {
//                    double soma = Math.abs(y1[j] - outLinha[j]);
//
//                    if (soma > 0) {
//                        erroOTeste += 1;
//                    } else {
//                        erroOTeste += 0;
//                    }
//
//                }
//            }
             for (int a = 0; a < baseTreinamento.length; a++) {
                //fazendo treino
                double[] x = baseTreinamento[a][0];
                double[] y = baseTreinamento[a][1];

                double[] teta = mlp.treinar(x, y);

                double erroAmostraAprox = 0;
                for (int j = 0; j < (y.length); j++) {
                    erroAmostraAprox += Math.abs(y[j] - teta[j]);
                }
                erroApTreino+= erroAmostraAprox;

                double[] outLinha = thresHold(teta);
                double sumLinha = 0;
                for (int j = 0; j < (y.length); j++) {
                    sumLinha += Math.abs(y[j] - outLinha[j]);
                }

                if(sumLinha > 0){
                    erroOTreino++;
                }

                if(a < baseTeste[0].length) {
                    //fazendo teste
                    x = baseTeste[a][0];
                    y = baseTeste[a][1];

                    double[] teste = mlp.testar(x, y);

                    for (int j = 0; j < (y.length); j++) {
                        erroApTeste += Math.abs(y[j] - teste[j]);
                    }

                    outLinha = thresHold(teste);
                    sumLinha = 0;
                    for (int j = 0; j < (y.length); j++) {
                        sumLinha += Math.abs(y[j] - outLinha[j]);
                    }

                    if (sumLinha > 0) {
                        erroOTeste++;
                    }
                }
            }
                     System.out.println("Época " + e + " - ErroAptreino: " + erroApTreino+ " - ErroOTreino: "+erroOTreino+" -ErroApTeste :" +erroApTeste+" -ErroOTeste :" +erroOTeste);
        
        }
         
    }

    public static double[] thresHold(double[] out) {
        double[] result = new double[out.length];
        for (int i = 0; i < out.length; i++) {
            result[i] = out[i] >= 0.5 ? 1.0 : 0.0;
        }
        return result;
    }

}
//            for (int a = 0; a < baseE.length; a++) {
//                double[] x = baseE[a][0];
//                double[] y = baseE[a][1];
//                  double[] teta = mlp.treinar(x, y);
//                double erroAmostraAprox = 0;
//                double[] teste = mlp.testar(x, y);
//                for (int j = 0; j < (y.length); j++) {
//                    erroAmostraAprox += Math.abs(y[j] - teta[j]);
//                    erroApTeste += Math.abs(y[j] - teste[j]);
//                }
//                erroApTreino += erroAmostraAprox;
//                double[] outLinha = thresHold(teta);
//                double[] testeLinha = thresHold(teste);
//                double soma = 0;
//                double soma1 = 0;
//                for (int j = 0; j < y.length; j++) {
//                    soma += Math.abs(y[j] - outLinha[j]);
//                    soma1 += Math.abs(y[j] - testeLinha[j]);
//                    if (soma > 0) {
//                        erroOTreino = 1;
//                    } else {
//                        erroOTreino = 0;
//                    }
//                }
//                if (soma > 0) {
//                    erroOTreino += 1;
//                } else {
//                    erroOTreino += 0;
//                }
//
//                if (soma1 > 0) {
//                    erroOTeste += 1;
//                } else {
//                    erroOTeste += 0;
//                }
//            }
//            for (int a = 0; a < baseOu.length; a++) {
//                double[] x = baseOu[a][0];
//                double[] y = baseOu[a][1];
//                double[] teta = mlp.treinar(x, y);
//                double erroAmostraAprox = 0;
//                double[] teste = mlp.testar(x, y);
//                for (int j = 0; j < (y.length); j++) {
//                    erroAmostraAprox += Math.abs(y[j] - teta[j]);
//                    erroApTeste += Math.abs(y[j] - teste[j]);
//                }
//                erroApTreino += erroAmostraAprox;
//                double[] outLinha = thresHold(teta);
//                double[] testeLinha = thresHold(teste);
//                double soma = 0;
//                double soma1 = 0;
//                for (int j = 0; j < y.length; j++) {
//                    soma += Math.abs(y[j] - outLinha[j]);
//                    soma1 += Math.abs(y[j] - testeLinha[j]);
//                    if (soma > 0) {
//                        erroOTreino = 1;
//                    } else {
//                        erroOTreino = 0;
//                    }
//                }
//                if (soma > 0) {
//                    erroOTreino += 1;
//                } else {
//                    erroOTreino += 0;
//                }
//
//                if (soma1 > 0) {
//                    erroOTeste += 1;
//                } else {
//                    erroOTeste += 0;
//                }
//
//            }