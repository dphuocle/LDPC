import java.io.*;

public class Main {

    public static Matrix loadMatrix(String file, int r, int c) {
        byte[] tmp =  new byte[r * c];
        byte[][] data = new byte[r][c];
        try {
            FileInputStream fos = new FileInputStream(file);
            fos.read(tmp);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < r; i++)
            for (int j = 0; j< c; j++)
                data[i][j] = tmp[i * c + j];
        return new Matrix(data);
    }

    public static void main(String[] arg){

        // Première tâche :
        System.out.println("\n-------------------------------------\tPremière tâche\t-------------------------------------\n");
        Matrix hbase = loadMatrix("data/matrix-15-20-3-4", 15, 20);

        System.out.println("Une matrice {{1,0,0},{0,1,0},{0,0,1}} :\n");
        byte[][] tab1 = {{1,0,0},{0,1,0},{0,0,1}};
        Matrix m1 = new Matrix(tab1);
        m1.display();

        System.out.println("Ajout de deux lignes : ligne 1 et ligne 2 :\n");
        m1.addRow(0, 1);
        m1.display();

        System.out.println("Multiplication de deux matrices : m1 et la transposée de m1 :\n");
        m1.multiply(m1.transpose()).display();

        System.out.println("Matrice de controle H :\n");
        hbase.display();

        System.out.println("Forme systématique de H :\n");
        hbase.sysTransform().display();

        System.out.println("Matrice génératrice G :\n");
        hbase.genG().display();

        byte[][] u = {{1,0,1,0,1}};
        System.out.println("Mot binaire u :");
        for(int i = 0; i < 5; i++) System.out.print(u[0][i] + " ");

        System.out.println("\n\nEncodage de u (x=u.G) :\n");
        Matrix x = new Matrix(u).multiply(hbase.genG());
        x.display();

        System.out.println("Syndrome de x (s=H.x^t) :\n");
        hbase.multiply(x.transpose()).transpose().display();

        // Tâche 2 :
        System.out.println("\n-------------------------------------\tDeuxième tâche\t-------------------------------------\n");
        TGraph T_graph = new TGraph(hbase, 3, 4);
        T_graph.display();

        System.out.println("\n-------------------------------------\n  Bruitage et correction du mot x :\n-------------------------------------\n");

        System.out.println("Mot de code x :");
        TGraph graph = new TGraph(hbase, 3, 4);
        graph.decode(x, 100).display();

        //Vecteur d'erreur e1
        byte[][] e1 = {{0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
        System.out.println("Vecteur d'erreurs e1 :\n");
        for(int i = 0; i < 20; i++) System.out.print(e1[0][i] + " ");

        System.out.println("\n\nMot de code bruité y1=x+e1 :\n");
        Matrix y1 = new Matrix(e1).add(x);
        y1.display();

        System.out.println("Syndrome de y1 :\n");
        hbase.multiply(y1.transpose()).transpose().display();

        System.out.println("Correction x1 de y1 :\n");
        TGraph x1 = new TGraph(hbase, 3, 4);
        x1.decode(y1, 100).display();

        System.out.println("x1 = x : " + x1.decode(y1, 100).isEqualTo(x));

        System.out.println("\n------------------------------------------------\n");

        //Vecteur d'erreur e2
        byte[][] e2 = {{0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
        System.out.println("Vecteur d'erreurs e2 :\n");
        for(int i = 0; i < 20; i++) System.out.print(e2[0][i] + " ");

        System.out.println("\n\nMot de code bruité y2=x+e2 :\n");
        Matrix y2 = new Matrix(e2).add(x);
        y2.display();

        System.out.println("Syndrome de y2 :\n");
        hbase.multiply(y2.transpose()).transpose().display();

        System.out.println("Correction x2 de y2 :\n");
        TGraph x2 = new TGraph(hbase, 3, 4);
        x2.decode(y2, 100).display();

        System.out.println("x2 = x : " + x2.decode(y2, 100).isEqualTo(x));

        System.out.println("\n------------------------------------------------\n");

        //Vecteur d'erreur e3
        byte[][] e3 = {{0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0}};
        System.out.println("Vecteur d'erreurs e3 :\n");
        for(int i = 0; i < 20; i++) System.out.print(e3[0][i] + " ");

        System.out.println("\n\nMot de code bruité y3=x+e3 :\n");
        Matrix y3 = new Matrix(e3).add(x);
        y3.display();

        System.out.println("Syndrome de y3 :\n");
        hbase.multiply(y3.transpose()).transpose().display();

        System.out.println("Correction x3 de y3 :\n");
        TGraph x3 = new TGraph(hbase, 3, 4);
        x3.decode(y3, 100).display();

        System.out.println("x3 = x : " + x3.decode(y3, 100).isEqualTo(x));

        System.out.println("\n------------------------------------------------\n");

        //Vecteur d'erreur e4
        byte[][] e4 = {{0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}};
        System.out.println("Vecteur d'erreurs e4 :\n");
        for(int i = 0; i < 20; i++) System.out.print(e4[0][i] + " ");

        System.out.println("\n\nMot de code bruité y4=x+e4 :\n");
        Matrix y4 = new Matrix(e4).add(x);
        y4.display();

        System.out.println("Syndrome de y4 :\n");
        hbase.multiply(y4.transpose()).transpose().display();

        System.out.println("Correction x4 de y4 :\n");
        TGraph x4 = new TGraph(hbase, 3, 4);
        x4.decode(y4, 100).display();

        System.out.println("x4 = x : " + x4.decode(y4, 100).isEqualTo(x));


        // TACHE 3
        System.out.println("\n-------------------------------------\n\t\tTroisième tâche\t\n-------------------------------------\n");
        Matrix hbase2 = loadMatrix("data/Matrix-2048-6144-5-15", 2048, 6144);
        Matrix hbase2genG = hbase2.genG();
        // hbase2genG.display();

        //Initialisation Matrice Error
        byte[][] motErr = null;
        motErr = new byte[1][hbase2.getCols()];
        for(int i = 0; i < hbase2.getCols(); i++) motErr[0][i] = -1;
        Matrix error = new Matrix(motErr);


        //Initialisation de u
        byte[][] mot_u = null;
        mot_u = new byte[1][hbase2genG.getRows()];
        boolean pair = true;
        for(int i = 0; i < hbase2genG.getRows(); i++)
        {
            if(pair)
            {
                mot_u[0][i] = (byte)0;
                pair = false;
            }
            else
            {
                mot_u[0][i] = (byte)1;
                pair = true;
            }
            // System.out.print(mot_u[0][i] + mot_u[0][i+1] +" ");
        }

        //Encodage de u
        Matrix xt3 = new Matrix(mot_u).multiply(hbase2genG);
        // x.display();
        //Génération du graphe de Tanner de H
        TGraph graph2 = new TGraph(hbase2, 5, 15);
        //graph2.display();

        for(int j = 0; j <= 3; j++)
        {
            int w = 124;
            if(j == 1)
                w = 134;
            else if(j == 2)
                w = 144;
            else if(j == 3)
                w = 154;

            int countOK = 0, countEchec = 0, countError = 0;
            for(int i = 0; i < 10000; i++)
            {
                Matrix e = xt3.errGen(w);
                Matrix y = xt3.add(e);
                if(graph2.decode(y, 200).isEqualTo(xt3))
                    countOK++;
                else if(graph2.decode(y, 200).isEqualTo(error))
                    countEchec++;
                else
                    countError++;
            }
            System.out.println("Pour w="+ w +",\n" +
                    "Nombre d'échecs : " + countEchec + "\n" + "Nombre d'erreurs : " + countError);
            double cas_critiques = 100-(double)countOK/10000*100;
            double echecs = (double)countEchec/10000*100;
            double erreur = (double)countError/10000*100;
            System.out.println("\n" + cas_critiques + "% de cas critiques dont :\n\t-"+ echecs+"% d'echecs\n\t-"+erreur+"% d'erreurs\n\n");
        }
    }
}
