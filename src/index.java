
import controller.SalaController;
import view.MainApp;
import vo.SalaVO;

/**
 *
 * @author maikon
 */
public class index {
    public static void main(String[] args) {
        
        //Início da aplicação
        SalaController controllerSala = new SalaController();
        SalaVO[] salas = controllerSala.CarregarSalas();
        
        SalaVO salaInicial = controllerSala.FindById("U");
        SalaVO salaFinal = controllerSala.FindById("I");
        
        String rotaSimulatedComManhattan = controllerSala.CalcularRotaSimulatedManhattan(salaInicial, salaFinal);
        String rotaSimulated = controllerSala.CalcularRotaSimulated(salaInicial, salaFinal);
        
        System.out.println("[Simulated com Manhattan]: Rota percorrida de " + salaInicial.getId() + " a " + salaFinal.getId() + ": " + rotaSimulatedComManhattan);
        System.out.println("[Simulated]: Rota percorrida de " + salaInicial.getId() + " a " + salaFinal.getId() + ": " + rotaSimulated);
        
        new MainApp().show();
    }
}
