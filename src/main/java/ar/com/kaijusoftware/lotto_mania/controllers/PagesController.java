package ar.com.kaijusoftware.lotto_mania.controllers;

import ar.com.kaijusoftware.lotto_mania.services.AuthService;
import ar.com.kaijusoftware.lotto_mania.services.MainframeProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PagesController {

    private static final Set<String> ALLOWED_PATHS = Set.of("login", "activate", "first");

    private final MainframeProductService mainframeProductService;

    private final AuthService authService;

    @GetMapping("/")
    public String index(final Model model, HttpServletRequest request) {
        if (mainframeProductService.isProductActivated()) {
            // if (!authService.isUserLoggedIn()) {
            return "redirect:/login";
            // } else {
            // return "redirect:/home";
            // }
        } else {
            return "redirect:/activate";
        }
    }

    @GetMapping("/{view}")
    public String page(@PathVariable("view") final String view, Model model) {
        // Redirect the /active to /home if the product is already activated
        if ("active".equalsIgnoreCase(view) && mainframeProductService.isProductActivated()) {
            return "redirect:/home";
        }

        // Checks if the view matches with any of the allowed paths
        if (ALLOWED_PATHS.stream().noneMatch(path -> path.equalsIgnoreCase(view))) {
            // NOTE: Should we really need to throw a 404? Why not a 403/401?
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        model.addAttribute("page", "fragments/" + view.toLowerCase());
        return "index";
    }
}
