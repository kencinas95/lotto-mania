package ar.com.kaijusoftware.lotto_mania.controllers;

import ar.com.kaijusoftware.lotto_mania.services.MainframeProductService;
import ar.com.kaijusoftware.lotto_mania.utils.BasicResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/mainframe")
public class MainframeProductController {
    private final MainframeProductService mainframeProductService;

    @PostMapping("/activate")
    public String activate(@RequestParam("username") final String username,
                           @RequestParam("password") final String password,
                           @RequestParam("key") final String key,
                           RedirectAttributes attrs) {
        BasicResult<Boolean> result = mainframeProductService.activate(key, username, password);
        if (result.isOk()) {
            attrs.addFlashAttribute("username", username);
            attrs.addFlashAttribute("password", password);
            attrs.addFlashAttribute("countries", Map.of("AR", "ARGENTINA"));
            return "redirect:/first";
        } else {
            attrs.addFlashAttribute("errMessage", result.getError().getMessage());
            return "redirect:/activate";
        }
    }

}
