package com.example.myapp.controller;

import com.example.myapp.entity.Person;
import com.example.myapp.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/")
    public String index(Model model) {
        List<Person> people = personRepository.findAll();
        model.addAttribute("people", people);
        return "index";
    }

    @GetMapping("/add")
    public String showAddForm(Person person) {
        return "addPerson";
    }

    @PostMapping("/add")
    public String addPerson(Person person) {
        personRepository.save(person);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid person Id:" + id));
        model.addAttribute("person", person);
        return "updatePerson";
    }

    @PostMapping("/update/{id}")
    public String updatePerson(@PathVariable("id") long id, Person person, Model model) {
        person.setId(id);
        personRepository.save(person);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deletePerson(@PathVariable("id") long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid person Id:" + id));
        personRepository.delete(person);
        return "redirect:/";
    }
}