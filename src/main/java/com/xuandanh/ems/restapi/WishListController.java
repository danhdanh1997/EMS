package com.xuandanh.ems.restapi;
import com.xuandanh.ems.common.ApiResponse;
import com.xuandanh.ems.domain.Product;
import com.xuandanh.ems.domain.User;
import com.xuandanh.ems.domain.WishList;
import com.xuandanh.ems.dto.product.ProductDto;
import com.xuandanh.ems.service.AuthenticationService;
import com.xuandanh.ems.service.ProductService;
import com.xuandanh.ems.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class WishListController {
    private WishListService wishListService;
    private AuthenticationService authenticationService;

    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token") String token) {
        int user_id = authenticationService.getUser(token).getId();
        List<WishList> body = wishListService.readWishList(user_id);
        List<ProductDto> products = new ArrayList<ProductDto>();
        for (WishList wishList : body) {
            products.add(ProductService.getDtoFromProduct(wishList.getProduct()));
        }

        return new ResponseEntity<List<ProductDto>>(products, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addWishList(@RequestBody Product product, @RequestParam("token") String token) {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        WishList wishList = new WishList(user, product);
        wishListService.createWishlist(wishList);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Add to wishlist"), HttpStatus.CREATED);

    }
}
