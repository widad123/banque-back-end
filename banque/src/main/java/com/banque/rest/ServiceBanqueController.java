package com.banque.rest;

import com.banque.entity.CompteEntity;
import com.banque.entity.ICompteEntity;
import com.banque.entity.IOperationEntity;
import com.banque.entity.OperationEntity;
import com.banque.service.IAuthentificationService;
import com.banque.service.ICompteService;
import com.banque.service.IOperationService;


import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/service-banque")
public class ServiceBanqueController {

    @Autowired
    @Qualifier("authentificationService")
    private IAuthentificationService serviceAuthentification;

    @Autowired
    @Qualifier("compteService")
    private ICompteService serviceCompte;

    @Autowired
    @Qualifier("operationService")
    private IOperationService serviceOperation;

    @PostMapping("/authentifier")
    public Integer authentifier(@RequestParam("unLogin") String unLogin, @RequestParam("unMdp") String unMdp) throws Exception {
        // NE JAMAIS FAIRE CELA DANS LA REALITE
        // NE JAMAIS LOGUER/AFFICHER LE MOT DE PASSE
        // Logique d'authentification
        // ...
        return serviceAuthentification.authentifier(unLogin, unMdp).getId();
    }

    @PostMapping("/selectCompte")
    public CompteEntity[] selectCompte(@RequestParam("unUtilisateurId") Integer unUtilisateurId) throws Exception {
        // Logique de sélection des comptes
        // ...
        List<ICompteEntity> comptes = serviceCompte.selectAll(unUtilisateurId);
        return comptes.toArray(new CompteEntity[0]);
    }

    @PostMapping("/selectOperation")
    public OperationEntity[] selectOperation(
        @RequestParam("unUtilisateurId") Integer unUtilisateurId,
        @RequestParam("unCompteId") Integer unCompteId,
        @RequestParam("dateDeb") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") Date dateDeb,
        @RequestParam("dateFin") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") Date dateFin,
        @RequestParam("creditDebit") Boolean creditDebit
    ) throws Exception {
        // Logique de sélection des opérations
        // ...
        List<IOperationEntity> operations = serviceOperation.selectCritere(unUtilisateurId, unCompteId, dateDeb, dateFin, creditDebit, !creditDebit);
        return operations.toArray(new OperationEntity[0]);
    }
    
    @PostMapping("/doVirement")
    public OperationEntity[] doVirement(
        @RequestParam("unUtilisateurId") Integer unUtilisateurId,
        @RequestParam("unCompteIdSrc") Integer unCompteIdSrc,
        @RequestParam("unCompteIdDst") Integer unCompteIdDst,
        @RequestParam("unMontant") Double unMontant
    ) throws Exception {
        // ...
        List<IOperationEntity> operations = serviceOperation.faireVirement(unUtilisateurId,unCompteIdSrc,unCompteIdDst,unMontant);
        return operations.toArray(new OperationEntity[0]);
    }
    
    @PostMapping("/allOperations")
    public OperationEntity[]  selectAll(
    		 @RequestParam("unUtilisateurId") Integer unUtilisateurId,
    	     @RequestParam("unCompteId") Integer unCompteId
    	     )throws Exception{
    	List<IOperationEntity> operations = serviceOperation.selectAll(unUtilisateurId,unCompteId);
        return operations.toArray(new OperationEntity[0]);
    } 
}
