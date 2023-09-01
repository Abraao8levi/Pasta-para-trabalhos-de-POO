

class Car {
    public int pass;
    public int passMax;
    public int gas;
    public int gasMax;
    public int km;

    public Car() {
        pass = 0;
        passMax = 2;
        gas = 0;
        gasMax = 100;
        km = 0;
    }

    public String toString() {
        return "pass: " + pass + ", gas: " + gas + ", km: " + km;
    }

    public void enter() {
        if (pass == passMax) {
            System.out.println("fail: limite de pessoas atingido");
        } else {
            pass++;
        }
    }

    public void leave() {
        if (pass == 0) {
            System.out.println("fail: nao ha ninguem no carro");
        } else {
            pass--;
        }
    }

 void fuel( int comb ) {
        this.gas += comb;
        if ( this.gas > this.gasMax ) {
            this.gas = this.gasMax;
        }
    }
    void drive(int km) {
        if ( this.pass == 0 ) {
            System.out.println("fail: nao ha ninguem no carro");
        } else if ( this.gas == 0 ) {
            System.out.println("fail: tanque vazio");
        } else if ( this.gas < km) {
            System.out.println("fail: tanque vazio apos andar " + this.gas + " km");
            this.km += this.gas;
            this.gas = 0;
        } else {
            this.km += km;
            this.gas -= km;
        }
    }
}