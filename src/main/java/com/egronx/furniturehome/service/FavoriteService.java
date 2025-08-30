package com.egronx.furniturehome.service;

import com.egronx.furniturehome.entity.Favorite;
import com.egronx.furniturehome.entity.Product;
import com.egronx.furniturehome.entity.User;
import com.egronx.furniturehome.repository.ProductRepository;
import com.egronx.furniturehome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavoriteService {

    UserRepository userRepository;
    ProductRepository productRepository;

    @Autowired
    public FavoriteService(UserRepository userRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public List<Favorite> getAllUserFavorite(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        List<Favorite> favorites;
        favorites = new ArrayList<>(user.getFavorites());
        return favorites;
    }


    public void addFavorite(Long productId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        Product product = productRepository.findById(productId);
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setProduct(product);
        user.addFavorite(favorite);
        userRepository.save(user);
    }


    public void deleteFavorite(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        user.getFavorites().removeIf(favorite -> favorite.getId().equals(id));
        userRepository.save(user);
    }
}
