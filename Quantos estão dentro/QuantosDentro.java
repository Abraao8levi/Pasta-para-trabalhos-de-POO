import java.util.Scanner;
import java.lang.Math;

class Ponto {
    float x;
    float y;
    
    void ler() {
        // "4 1"
        // v = ["4","1"]
        String linha = input();
        String v[] = linha.split(" ");
        x = Float.parseFloat( v[0] );
        y = Float.parseFloat( v[1] );
    }
    
    float distancia( Ponto ponto ) {
        float cH = this.x - ponto.x;
        float cV = this.y - ponto.y;
        float dist = (float) Math.sqrt( cH*cH + cV*cV );
        return dist;
    }
    
    boolean estaDentro( Circulo circ ) {
        if ( this.distancia( circ.centro ) <= circ.raio ) {
            return true;
        } else {
            return false;
        }
    }

    String input() { return QuantosDentro.in.nextLine(); }
    void println( String str ) { System.out.println( str ); }
}

class Circulo {
    Ponto centro;
    float raio;

    void ler() {
        String linha = input();
        String v[] = linha.split(" ");
        centro = new Ponto();
        centro.x = Float.parseFloat( v[0] );
        centro.y = Float.parseFloat( v[1] );
        raio = Float.parseFloat( v[2] );
    }
    
    boolean contem( Ponto ponto ) {
        if ( this.centro.distancia( ponto ) <= this.raio ) {
            return true;
        } else {
            return false;
        }
    }

    int quantosDentro( Ponto[] vetor ) {
        int cont = 0;
        // for ( int i=0; i<vetor.length; i++ ) {
        //     Ponto ponto = vetor[i];
        for ( Ponto ponto : vetor ) {
            if ( this.contem( ponto ) ) { //if ( ponto.estaDentro( this ) ) {
                cont++;
            }
        }
        return cont;
    }

    String input() { return QuantosDentro.in.nextLine(); }
    void println( String str ) { System.out.println( str ); }
}

class QuantosDentro {
    public static void main(String[] arg) {
        int n = Integer.parseInt( input() );
        Ponto vetor[] = new Ponto[n];
        for (int i=0; i<vetor.length; i++) {
        	vetor[i] = new Ponto();
            vetor[i].ler();
        }
        Circulo circ = new Circulo();
        circ.ler();
        
        int cont = circ.quantosDentro( vetor );
        //println( "" + cont );
        System.out.printf( "%d", cont );
    }

    static Scanner in = new Scanner(System.in);
    static String input() { return in.nextLine(); }
    static void println( String str ) { System.out.println( str ); }
}
