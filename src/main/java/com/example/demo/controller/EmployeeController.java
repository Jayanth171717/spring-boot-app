package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employees"; // Display all employees
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "add-employee"; // Form for adding employee
    }

    @PostMapping("/save")
    public String saveEmployee(@Valid @ModelAttribute Employee employee, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "add-employee";
        }
        employeeService.saveEmployee(employee);
        redirectAttributes.addFlashAttribute("message", "Employee added successfully!");
        return "redirect:/employees";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        employeeService.deleteEmployee(id);
        redirectAttributes.addFlashAttribute("message", "Employee deleted successfully!");
        return "redirect:/employees";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            return "redirect:/employees"; // Redirect if employee not found
        }
        model.addAttribute("employee", employee);
        return "edit-employee"; // Display edit form
    }

    // Update employee
    @PostMapping("/update")
    public String updateEmployee(@Valid @ModelAttribute Employee employee, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "edit-employee";
        }
        employeeService.updateEmployee(employee);
        redirectAttributes.addFlashAttribute("message", "Employee updated successfully!");
        return "redirect:/employees";
    }
}


