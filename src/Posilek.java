import java.time.LocalDate;

public class Posilek {
    String nazwa;
    int kalorie,id;
    double weglowodany,tluszcze,bialko;
    LocalDate data;

    public Posilek(String nazwa, int kalorie, double weglowodany, double tluszcze, double bialko, LocalDate data, int id){
        this.nazwa = nazwa;
        this.kalorie = kalorie;
        this.weglowodany = weglowodany;
        this.tluszcze = tluszcze;
        this.bialko = bialko;
        this.data = data;
        this.id = id;
    }

    @Override
    public String toString(){
        return nazwa + " | kcal: " + kalorie + " | W: " + this.weglowodany + "g | T: " + this.tluszcze + "g | B: " + this.bialko + "g";
    }

    public int GetId(){
        return this.id;
    }
}
