package com.egronx.furniturehome.controller;

import com.egronx.furniturehome.entity.Favorite;
import com.egronx.furniturehome.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/favorite")
@PreAuthorize("isAuthenticated()")
public class FavoriteController {

    final
    FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }


    @GetMapping
    public List<Favorite> getFavorites() {
      return favoriteService.getAllUserFavorite();
    }

    @PostMapping
    public void addFavorite(@RequestParam Long productId) {
        favoriteService.addFavorite(productId);
    }


    @DeleteMapping("/{id}")
    public void deleteFavorite(@PathVariable Long id) {
        favoriteService.deleteFavorite(id);
    }
}
