package org.launchcode.controllers;

import org.launchcode.models.Employer;
import org.launchcode.models.Job;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        model.addAttribute("job",jobData.findById(id));

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        model.addAttribute("title", "Add Job");
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors,
                      @RequestParam String name, int employerId,
                      int locationId, int positionTypeId, int coreCompetencyId) {

        if (errors.hasErrors()) {
            model.addAttribute("title","Add Job");
            return "new-job";
        }

        Job newJob = new Job(name, jobData.getEmployers().findById(employerId),
                jobData.getLocations().findById(locationId),
                jobData.getPositionTypes().findById(positionTypeId),
                jobData.getCoreCompetencies().findById(coreCompetencyId));
        jobData.add(newJob);

        model.addAttribute("job",jobData.findById(newJob.getId()));
        return "redirect:/job/?id=" + newJob.getId();

    }
}
