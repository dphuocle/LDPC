public class TGraph {
    int[][] left = null, right = null;
    private int n_r = 0, w_r = 0, n_c = 0, w_c = 0;
    public TGraph(Matrix H, int wc, int wr) {
        w_r = wr;
        w_c = wc;
        n_r = H.getRows();
        n_c = H.getCols();

        left = new int[n_r][w_r + 1];
        right = new int[n_c][w_c + 1];

        int i = 0, j = 0;
        for(i = 0 ; i < n_r; i++) {
            left[i][0] = 0;
            right[i][0] = 0;
        }
        //left table
        for(i = 0 ; i < n_r; i++) {
            int z = 1;
            for(j = 0; j < n_c; j++) {
                if (H.getElem(i, j) == 1) {
                    left[i][z] = j;
                    z++;
                }
                if (z == n_c)
                    break;
            }
        }

        //right table
        for(j = 0; j < n_c; j++) {
            int z = 1;
            for(i = 0 ; i < n_r; i++) {
                if (H.getElem(i, j) == 1) {
                    right[j][z] = i;
                    z++;
                }
                if (z == n_c)
                    break;
            }
        }
    }

    public void display() {
        System.out.println("Graphe de Tanner :\n");
        int i, j, k, l;
        for(i = 0 , k = 0; i < n_c; i++, k++) {
            for(j = 0; j < w_c + 1; j++) {
                if(j == 0)
                    System.out.print(right[i][j] + "| ");
                else if (right[i][j] >= 10)
                    System.out.print(right[i][j] + " ");
                else
                    System.out.print(" " + right[i][j] + " ");
            }

            System.out.print("\t\t\t");
            if (k < n_r) {
                for(l = 0; l < w_r + 1; l++) {
                    if(l == 0)
                        System.out.print(left[k][l] + "| ");
                    else if (left[k][l] >= 10)
                        System.out.print(left[k][l] + " ");
                    else
                        System.out.print(" " + left[k][l] + " ");
                }
            }
            System.out.println();
        }
    }

    public Matrix decode(Matrix code, int rounds) {
        Matrix result = new Matrix(code.getRows(), code.getCols());
        boolean flag;
        int[]count = new int[this.n_c];

        for(int i = 0; i < this.n_c; i++) {
            this.right[i][0] = code.getElem(0, i);
        }

        for(int lim = 0; lim < rounds; lim++) {
            flag = false;
            for(int i = 0; i < this.n_r; i++) {
                this.left[i][0] = 0;

                for(int par = 1; par < this.w_r + 1; par++) {
                    this.left[i][0] += this.right[this.left[i][par]][0];
                    this.left[i][0] %= 2;
                }
                if(this.left[i][0] != 0)
                    flag = true;
            }
            if(!flag) {
                for(int i = 0; i < this.n_c; i++)
                    result.setElem(0, i, (byte)this.right[i][0]);
                return result;
            }

            for(int i = 0; i < this.n_c; i++)
                count[i]=0;
            int max_error = 0;
            for(int i = 0; i < n_r ; i++) {
                if(this.left[i][0] != 0) {
                    for(int j=1; j<this.w_r+1; j++) {
                        count[this.left[i][j]]++;
                        max_error = Math.max(max_error, count[this.left[i][j]]);
                    }
                }
            }
            for(int i=0; i<this.n_c; i++) {
                if(count[i] == max_error)
                    this.right[i][0] = 1-this.right[i][0];
            }
        }
        for(int i = 0; i < this.n_c; i++)
            result.setElem(0, i, (byte) -1);
        return result;
    }
}
