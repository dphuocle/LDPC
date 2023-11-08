import java.util.*;
import java.io.*;

public class Matrix {
    private byte[][] data = null;
    private int rows = 0, cols = 0;
    
    public Matrix(int r, int c) {
        data = new byte[r][c];
        rows = r;
        cols = c;
    }
    
    public Matrix(byte[][] tab) {
        rows = tab.length;
        cols = tab[0].length;
        data = new byte[rows][cols];
        for (int i = 0 ; i < rows ; i ++)
            for (int j = 0 ; j < cols ; j ++) 
                data[i][j] = tab[i][j];
    }
    
    public int getRows() {
        return rows;
    }
    
    public int getCols() {
        return cols;
    }
    
    public byte getElem(int i, int j) {
        return data[i][j];
    }
    
    public void setElem(int i, int j, byte b) {
        data[i][j] = b;
    }
    
    public boolean isEqualTo(Matrix m) {
        if ((rows != m.rows) || (cols != m.cols))
            return false;
        for (int i = 0; i < rows; i++) 
            for (int j = 0; j < cols; j++) 
                if (data[i][j] != m.data[i][j])
                    return false;
                return true;
    }
    
    public void shiftRow(int a, int b) {
        byte tmp = 0;
        for (int i = 0; i < cols; i++){
            tmp = data[a][i];
            data[a][i] = data[b][i];
            data[b][i] = tmp;
        }
    }
    
    public void shiftCol(int a, int b) {
        byte tmp = 0;
        for (int i = 0; i < rows; i++){
            tmp = data[i][a];
            data[i][a] = data[i][b];
            data[i][b] = tmp;
        }
    }

    public void display() {
        System.out.print("[");
        for (int i = 0; i < rows; i++) {
            if (i != 0) {
                System.out.print(" ");
            }
            
            System.out.print("[");
            
            for (int j = 0; j < cols; j++) {
                System.out.printf("%d", data[i][j]);
                
                if (j != cols - 1) {
                    System.out.print(" ");
                }
            }
            
            System.out.print("]");
            
            if (i == rows - 1) {
                System.out.print("]");
            }
            
            System.out.println();
        }
        System.out.println();
    }
    
    public Matrix transpose() {
        Matrix result = new Matrix(cols, rows);
        
        for (int i = 0; i < rows; i++) 
            for (int j = 0; j < cols; j++) 
                result.data[j][i] = data[i][j];
    
        return result;
    }
    
    public Matrix add(Matrix m) {
        Matrix r = new Matrix(rows,m.cols);
        
        if ((m.rows != rows) || (m.cols != cols))
            System.out.printf("Erreur d'addition\n");
        
        for (int i = 0; i < rows; i++) 
            for (int j = 0; j < cols; j++) 
                r.data[i][j] = (byte) ((data[i][j] + m.data[i][j]) % 2);
        return r;
    }
    
    public Matrix multiply(Matrix m) {
        Matrix r = new Matrix(rows,m.cols);
        
        if (m.rows != cols)
            System.out.printf("Erreur de multiplication\n");
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                r.data[i][j] = 0;
                for (int k = 0; k < cols; k++) {
                    r.data[i][j] =  (byte) ((r.data[i][j] + data[i][k] * m.data[k][j]) % 2);
                }
            }
        }
        
        return r;
    }

    public void addRow(int a, int b) {
        for (int i = 0; i < cols; i++) {
            data[b][i] = (byte) ((data[b][i] + data[a][i]) % 2);
        }
    }

    public void addCol(int a, int b) {
        for (int i = 0; i < rows; i++) {
            data[i][b] = (byte) ((data[i][b] + data[i][a]) % 2);
        }
    }

    public Matrix sysTransform() {
        Matrix h = new Matrix(data);
        for (int i = 0, col = cols - rows; i < rows && col < cols; i++, col++) {
            if (h.data[i][col] == 0) {
                for (int j = i + 1; j < rows; j++) {
                    if (h.data[j][col] == 1) {
                        h.shiftRow(i, j);
                        break;
                    }
                }
            }
            for (int k = i + 1; k < rows; k++) {
                if (h.data[k][col] == 1) {
                    h.addRow(i, k);
                }
            }
        }
        for (int j = cols - 1, i = rows - 1; j >= cols - rows && i >= 0; j--, i--) {
            for(int k = i - 1; k >= 0; k--) {
                if (h.data[k][j] == 1) {
                    h.addRow(i, k);
                }
            }
        }

        return h;
    }

    public Matrix genG() {
        Matrix a = new Matrix(data).sysTransform();
        Matrix b = new Matrix(a.data).transpose();

        Matrix g = new Matrix(a.cols-a.rows,a.rows+(a.cols-a.rows));
        for (int i = 0; i < g.rows; i++) {
            g.data[i][i] = 1;
        }
        for (int i = 0; i < g.rows; i++) {
            for (int j_g = g.rows, j = 0; j_g < g.cols && j < b.cols; j_g++, j++) {
                g.data[i][j_g] = b.data[i][j];
            }
        }
        return g;
    }

    public Matrix errGen(int w) {
        byte[][] code = null;
        code = new byte[1][this.getCols()];
        int min_val = 0;
        int max_val = 6144;
        int i = 0;

        for(i = 0; i < this.getCols(); i++) {
            code[0][i] = (byte)0;
        }

        for(i = 0; i < w; i++) {
            Random ran = new Random();
            int x = ran.nextInt(max_val) + min_val;

            if(code[0][x] == 0) {
                code[0][x] = 1;
            } else {
                w++;
            }
        }
        Matrix result = new Matrix(code);
        return result;
    }
}

