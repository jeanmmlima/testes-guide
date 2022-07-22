
public class ExemploTesteEstrutural {
    public Triangulo(String args[ ]){
        int a,b,c;
        String resp = null;
        a = Integer.parseInt(args[0]);
        b = Integer.parseInt(args[1]);
        c = Integer.parseInt(args[2]);
        if ((a==b)&&(b==c))
            resp = "equilatero";    
        if (((a==b)&&(b!=c))||((b==c)&&(a!=b))||((a==c)&&(c!=b)))
            resp = "isoceles"; 
        if ((a!=b)&&(b!=c))
         resp = "escaleno";
        System.out.println("Tipo de triangulo:"+resp);
    }

    public static void verificaOuCriaDirVeiculos() {
        File f = new File(Revenda.getDirVeiculos());
        if (f.exists()) {
            if (f.isDirectory())
                return;
            JOptionPane.showMessageDialog(null,
                    "Nao e possivel criar o diretorio veiculos" +
                            "\nRemova o arquivo com este nome diretório " +
                            "corrente");
            System.exit(0);
        } else {
            f.mkdir();
            File fImg = new File("NoVehicle.jpg");
            JTextField cmps[] = new JTextField[Veiculo.QTD];
            for (int i = 0; i < Veiculo.QTD; i++)
                cmps[i] = new JTextField(20);
            campos[Veiculo.PLACA].setText("NoVehicle");
            Veiculo.save(cmps, fImg);
        }
    }

    public exemplo1(String args[]){
        a = Integer.parseInt(args[0]);
        b = Integer.parseInt(args[1]);
        while (a < 0) {
                if (b < 0) {
                    b = b + 2;
                }
                a = a + 1;
        }
        c = a + b;
    }

    public void bolha(int[] a, int size) {
        int i, j, aux;
        for (i = 0; i < size; i++) {
            for (j = size - 1; j > 1; j--) {
                if (a[j - 1] > a[j]) {
                    aux = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = aux;
                }
            }
        }
    }
}
