package estefaniagg.blecontrol.Data;

public class Teclas {
    public static int getTeclaid(int codigo){
        int cod = 0;
        //TODO: acabar teclado de símbolos
        switch (codigo) {
            //Símbolos
            case 62: cod = 32; break; //espacio
            case 59: cod = 129; break; //shift
            case 66: cod = 176; break; //enter
            case 67: cod = 178; break;
            //Numeros
            case 7: cod = 48; break;
            case 8: cod = 49; break;
            case 9: cod = 50; break;
            case 10: cod = 51; break;
            case 11: cod = 52; break;
            case 12: cod = 53; break;
            case 13: cod = 54; break;
            case 14: cod = 55; break;
            case 15: cod = 56; break;
            case 16: cod = 57; break;
            //Letras
            case 29: cod = 97; break;
            case 30: cod = 98; break;
            case 31: cod = 99; break;
            case 32: cod = 100; break;
            case 33: cod = 101; break;
            case 34: cod = 102; break;
            case 35: cod = 103; break;
            case 36: cod = 104; break;
            case 37: cod = 105; break;
            case 38: cod = 106; break;
            case 39: cod = 107; break;
            case 40: cod = 108; break;
            case 41: cod = 109; break;
            case 42: cod = 110; break;
            case 43: cod = 111; break;
            case 44: cod = 112; break;
            case 45: cod = 113; break;
            case 46: cod = 114; break;
            case 47: cod = 115; break;
            case 48: cod = 116; break;
            case 49: cod = 117; break;
            case 50: cod = 118; break;
            case 51: cod = 119; break;
            case 52: cod = 120; break;
            case 53: cod = 121; break;
            case 54: cod = 122; break;
            default: cod = 0 ; break;
        }
        return cod;
    }
}
