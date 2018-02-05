package pe.com.sample.presenter;

import pe.com.sample.interactor.ComparatorInteractor;
import pe.com.sample.view.ComparatorView;

public class ComparatorPresenter {

    private ComparatorView comparatorView;
    private ComparatorInteractor comparatorInteractor;

    public ComparatorPresenter(ComparatorView comparatorView){
        this.comparatorView = comparatorView;
        this.comparatorInteractor = new ComparatorInteractor(this);
    }

    public void generateExcel(){
        /*CREAR LISTA DE COMPARACION*/
        comparatorView.setTotalProgressBar(11);
        comparatorView.showDialogLoadOperation();
        comparatorInteractor.generateExcel();
    }

    public void progressBarPlus(){
        comparatorView.plusProgressBar();
    }

    public void generateSuccess() {
        comparatorView.closeDialogLoadOperation();
        comparatorView.resetProgressBar();
        comparatorView.showMessageSuccess("Se genero el Excel satisfactoriamente");
    }

    public void generateError() {
    }
}
