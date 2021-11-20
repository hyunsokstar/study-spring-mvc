package hello.itemservice.web.validation;
import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Slf4j
@Controller
@RequestMapping("/validation/v3/items")
@RequiredArgsConstructor
public class ValidationItemControllerV3 {

    // 스프링에서 자동 주입해주는 빈을 사용
    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;

    // 검증 로직 등록
    // 스프링에서 제공하는 WebDataBinder 객체를 이용해 검증 로직을 등록하면 @Validated 어노테이션 적용만으로 쉽게 검증 로직을 적용하는게 가능 해진다.
//    @InitBinder
//    public void init(WebDataBinder dataBinder) {
//        log.info("init binder {}", dataBinder);
//        dataBinder.addValidators(itemValidator);
//    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v3/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v3/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v3/addForm";
    }

    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        System.out.println("상품 입력 요청 확인 !!!!!!!!!!!!!!!!!!!!!!!!!");
        log.info("objectName={}", bindingResult.getObjectName());
        log.info("target={}", bindingResult.getTarget());


        //특정 필드 예외가 아닌 전체 예외
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000,
                        resultPrice}, null);
            }
        }


        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "validation/v3/addForm";
        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v3/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v3/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@Validated @PathVariable Long itemId, @ModelAttribute Item item, BindingResult bindingResult, Model mode) {
        System.out.println("수정 포스트 요청 확인 !!!!!!!!!!2222");

        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000,resultPrice}, null);
            }
        }



        if(item.getItemName() == null ){
            System.out.println("상품명이 없습니다");
        } else if(item.getItemName() != null){
            System.out.println("item.getItemName : "+ item.getItemName());
        }

        if(bindingResult.hasErrors()) {
            System.out.println("에러 확인 ?????????????????????");
            log.info("errors={}", bindingResult);
            return "validation/v3/editForm";
        } else if (!bindingResult.hasErrors()){
            System.out.println("에러 없음");
        }

        itemRepository.update(itemId, item);
        return "redirect:/validation/v3/items/{itemId}";
    }

}
