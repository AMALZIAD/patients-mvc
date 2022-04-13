package ma.enset.patientsmvc.web;

import lombok.AllArgsConstructor;
import ma.enset.patientsmvc.entities.Patient;
import ma.enset.patientsmvc.repositories.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

    // get the index page xith pagination-----------------------INDEX---------------------------------
    @GetMapping(path = "/user/index")
    // seet Request params and keep the same name so we dont use @request param again
    public String patients(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "5") int size,
                           @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        Page<Patient> pagepatients = patientRepository.findByNomContains(keyword, PageRequest.of(page, size));
        model.addAttribute("listPatients", pagepatients.getContent());
        model.addAttribute("pages", new int[pagepatients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "patients";
    }
    // ---------------------------------------------------------------------/----------------------------------------------------

    // if the user just tape the link of website without the specifi page we return the index
    @GetMapping(path = "/")
    public String home() {
        return "home";
    }

    // get Json response---------------------------------------------------------JSON RESPONSE---------------------------------*-
    @GetMapping("/user/patients")
    @ResponseBody
    public List<Patient> Listpatients() {
        return patientRepository.findAll();
    }

    // ajout patient---------------------------------------------------------------ADD PATIENT----------------------------------
    //get the html page request the page // when user rquest formpatient
    // the controller return a html page with model with object patient
    @GetMapping(path = "/admin/formPatient")
    public String formPatient(Model model) {
        model.addAttribute("patient", new Patient());
        return "formPatient";
    }

    //------------------------------------------------------------------------------------EDIT PATIENT--------------------------------
    // when user click on lick to edit patient we get the id page and keyword in QUERY PARAM
    @GetMapping(path = "/admin/editPatient")
    public String editPatient(Model model, Long id, String keyword, int page) {
        //add data from request to the model and send the html page to the client
        Patient patient=patientRepository.findById(id).orElse(null);
        if (patient==null) throw new RuntimeException("Patient introuvable");
        model.addAttribute("patient",patient);// Patient data
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "editPatient";
    }

    //  delete link------------------------------------------------------------------------DELETE PATIENT-------------------------
    @GetMapping(path = "/admin/delete")
    // get the patient id to delete keyword and page to keep the same situation before dletting
    public String delete(Long id, String keyword, int page) {
        patientRepository.deleteById(id);
        String s = "redirect:/user/index?page=" + page + "&keyword=" + keyword;
        return s;
    }

    //---------------------------------------------------------------------------------------SAVE OPERATION---------------------------
    // when the user click on save button
    @PostMapping(path = "/admin/save")
    public String save(Model model, @Valid Patient patient, BindingResult bindingResult,
                       @RequestParam(name = "page", defaultValue = "0") int page,
                       @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        String str = "";
        if (patient.getId() == null)
            str = "formPatient";
        else str = "editPatient";

        if (bindingResult.hasErrors())
            return str;
        patientRepository.save(patient);
        return "redirect:/user/index?page=" + page + "&keyword=" + keyword;
    }



}



