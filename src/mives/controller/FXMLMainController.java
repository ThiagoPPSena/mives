/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mives.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import mives.controller.helpers.FXMLCarregarLivroControllerHelper;
import mives.controller.FXMLCarregarLivroController;
import mives.controller.FXMLProcessandoLivroController;
import mives.controller.helpers.MainControllerHelper;
import mives.controller.helpers.utils.MivesWizardData;
import mives.controller.helpers.utils.PageWizard;
import mives.controller.helpers.utils.Revista;
import mives.model.Livro;
import mives.model.MapaConfiguracao;

/**
 *
 * @author Ricardo
 */
public class FXMLMainController implements Initializable, PageWizard, Observer {
    
    private MainControllerHelper helper;
    
    private FXMLCarregarLivroControllerHelper carregarLivroHelper;
    
    private FXMLCarregarLivroController carregarLivro;
    
    private FXMLProcessandoLivroController processar;
    
    @FXML
    protected StackPane stack;
    
    @FXML
    public Button btnSair;
    
    @FXML
    public Button btnProximo;
    
    @FXML
    public Button btnVoltar;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        helper = new MainControllerHelper(this);
        helper.loadNodes();
        
        btnVoltar.setDisable(true);
        btnVoltar.setOpacity(0);
        Revista.registrarAssinante(this);
        
        FXMLCarregarLivroController.helper.addObserver(this); //Adicionando essa classe como observadora da classe de carregar livro
        //   btnFinalizar.setDisable(true);
    }
    
    public void update(Observable obj, Object arg) {
    	System.out.println("Update");
    	this.btnProximo.setDisable((Boolean)arg);
    }
    
    public void nextPage() {
        helper.nextPage(6);
    }
    
    @FXML
    protected void nextPage(ActionEvent e) {
    	
    	//Se for a primeira página e eu apertei Avançar
    	if(helper.getCurPageIdx() == 0) {
    		btnVoltar.setDisable(false); //botão de voltar abilitado
            btnVoltar.setOpacity(1); //botão de voltar aparece na tela
            if(FXMLCarregarLivroController.arquivo != null) {
            	System.out.println(FXMLCarregarLivroController.arquivo.getName());//se não tiver arquivo, desabilita Avançar
    			btnProximo.setDisable(false);
    		}else { //caso contrário
    			btnProximo.setDisable(true);
    		}
    	}
    	
    	// Se a página for 1 e apertei avançar, carrega o livro selecionado
    	if(helper.getCurPageIdx() == 1) { 
    		FXMLCarregarLivroController.helper.iniciarCarregarLivro();
    		btnProximo.setDisable(true);
    		
    	}
    	
    	//Se estou na página 2 e avanço e não tem parâmetros definidos, botão de avançar desabilitado
    	if(helper.getCurPageIdx() == 2 && MapaConfiguracao.getMapaConfiguracao()==null) {
    		System.out.println("Vazio");
    		btnProximo.setDisable(true);
    	//Se estou na página 2 e avanço e tem parâmetros definidos, botão de avançar abilitado
    	}else if(helper.getCurPageIdx() == 2 && MapaConfiguracao.getMapaConfiguracao()!=null) {
    		System.out.println("Não Vazio");
    		btnProximo.setDisable(false);
    	}
    	
    	if(helper.getCurPageIdx() == 5) {
    		FXMLProcessandoLivroController.processarLivro();
    		btnVoltar.setDisable(true);
    		btnVoltar.setVisible(false);
    		btnProximo.setDisable(true);
    		btnProximo.setVisible(false);
    	}
    	
    	if(helper.getCurPageIdx() == 6) {
    		btnVoltar.setDisable(true);
    		btnVoltar.setVisible(false);
    		btnProximo.setDisable(true);
    		btnProximo.setVisible(false);
    	}
    	
        helper.nextPage();
    }
    
    @FXML
    protected void priorPage(ActionEvent e) {
    	if(helper.getCurPageIdx() == 1) {
    		btnVoltar.setDisable(true);
            btnVoltar.setOpacity(0);
            btnProximo.setDisable(false);
    	}
    	
    	if(helper.getCurPageIdx() == 3) {
    		btnProximo.setDisable(false);
    	}
    
    	if(helper.getCurPageIdx() == 4 && MapaConfiguracao.getMapaConfiguracao()==null) {
    		System.out.println("Vazio");
    		btnProximo.setDisable(true);
    	}else if(helper.getCurPageIdx() == 4 && MapaConfiguracao.getMapaConfiguracao()!=null) {
    		System.out.println("Não Vazio");
    		btnProximo.setDisable(false);
    	}
    	
    	if(helper.getCurPageIdx() == 6) {
    		btnProximo.setDisable(false);
    	}
    	
    	if(helper.getCurPageIdx() == 7) {
    		btnProximo.setDisable(true);
    	}
    	
        helper.priorPage();
    }
    
    public StackPane getStackPane() {
        return stack;
    }
    
    //Ao apertar o botão de SAIR, fecha a aplicação//
    @FXML
    protected void fecharAplicacao(ActionEvent e) {
    	Stage stage = (Stage) btnSair.getScene().getWindow(); //Obtendo a janela atual//
        stage.close();
    }
        
    @Override
    public void update() {
        if (MivesWizardData.isHabilitarBotaoFinalizar()) {
        	btnSair.setDisable(false);
        }
    }
    
}
