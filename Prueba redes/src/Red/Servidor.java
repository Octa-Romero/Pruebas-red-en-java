package Red;

import Hilos.HiloServidor;

public class Servidor {

    private HiloServidor hs;

    public Servidor()
    {
        hs = new HiloServidor();
        hs.start();
    }
}
