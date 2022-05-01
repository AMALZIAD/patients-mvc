package ma.enset.patientsmvc.web;


import lombok.AllArgsConstructor;
import ma.enset.patientsmvc.entities.Medecin;
import ma.enset.patientsmvc.entities.Patient;
import ma.enset.patientsmvc.repositories.MedecinRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@AllArgsConstructor // injection par constructeur
public class MedecinController {
    private MedecinRepository medecinRepository;


    // get the indexP page xith pagination-----------------------indexP---------------------------------
    @GetMapping(path = "/user/indexM")
    // seet Request params and keep the same name so we dont use @request param again
    public String medecins(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "5") int size,
                           @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        Page<Medecin> pagemedecins = medecinRepository.findByNomContains(keyword, PageRequest.of(page, size));
        model.addAttribute("listMedecins", pagemedecins.getContent());
        model.addAttribute("pages", new int[pagemedecins.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "medecins";
    }
    @GetMapping(path = "/admin/deleteM")
    public String delete(Long id,int page,String keyword){
        medecinRepository.deleteById(id);
        return  "redirect:/user/indexM?page=" + page + "&keyword=" + keyword;
    }

    ///-----------------------------------------------FOR MEDECIN-----------
    @GetMapping(path = "/admin/formMedecin")
    public String formMedecin(Model model  ){
        model.addAttribute("medecin", new Medecin());
        return "formMedecin" ;
    }

    @PostMapping(path = "/admin/saveM")
    public String save(Model model , @Valid Medecin medecin ){
        medecinRepository.save(medecin);
        return  "redirect:/user/indexM?" +  "keyword=" + medecin.getNom();
    }
}
