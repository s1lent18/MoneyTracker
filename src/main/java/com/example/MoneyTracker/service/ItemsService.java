package com.example.MoneyTracker.service;

import com.example.MoneyTracker.models.model.Items;
import com.example.MoneyTracker.models.model.Users;
import com.example.MoneyTracker.models.repository.ItemsRepository;
import com.example.MoneyTracker.models.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
@Service
public class ItemsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemsRepository itemsRepository;

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddItemsRequest {
        public String name;
        public Integer price;
        public Integer quantity;
    }

    @Setter @Getter @NoArgsConstructor @AllArgsConstructor
    public static class AddItemsResponse {
        public Integer id;
        public String name;
        public Integer price;
        public Integer quantity;
    }

    public AddItemsResponse addItem(Integer userId, AddItemsRequest request) {

        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));

        Items item = new Items(request.name, request.price, request.quantity, user);

        Items savedItem = itemsRepository.save(item);

        user.setTotal(user.getTotal() + request.price);
        user.setBudget(user.getBudget() - request.price);

        userRepository.save(user);

        return new AddItemsResponse(
                savedItem.getId(),
                savedItem.getName(),
                savedItem.getPrice(),
                savedItem.getQuantity()
        );
    }

    public String deleteItem(Integer userId, Integer id) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));

        for (Items item : user.getItems()) {
            if (Objects.equals(item.getId(), id)) {
                itemsRepository.deleteById(id);
                return "Successfully Deleted";
            }
        }

        return "Id Not Found";
    }
}