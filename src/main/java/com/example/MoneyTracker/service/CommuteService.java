package com.example.MoneyTracker.service;

import com.example.MoneyTracker.models.model.Commute;
import com.example.MoneyTracker.models.model.Users;
import com.example.MoneyTracker.models.repository.CommuteRepository;
import com.example.MoneyTracker.models.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CommuteService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommuteRepository commuteRepository;

    @Setter @Getter @NoArgsConstructor @AllArgsConstructor
    public static class AddCommuteRequest {
        public String route;
        public Integer price;
    }

    @Setter @Getter @NoArgsConstructor @AllArgsConstructor
    public static class AddCommuteResponse {
        public Integer id;
        public String route;
        public Integer price;
    }

    public List<AddCommuteResponse> getCommutes(Integer userId) {

        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));

        List<AddCommuteResponse> response = new ArrayList<>();

        for (Commute route : user.getCommutes()) {
            AddCommuteResponse r = new AddCommuteResponse(route.getId(), route.getRoute(), route.getPrice());
            response.add(r);
        }

        return response;
    }

    public AddCommuteResponse addCommuteRoute(Integer userId, AddCommuteRequest request) {

        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));

        Commute route = new Commute(request.route, request.price, user);

        Commute savedRoute = commuteRepository.save(route);

        user.setTotal(user.getTotal() + request.price);
        user.setBudget(user.getBudget() - request.price);

        userRepository.save(user);

        return new AddCommuteResponse(
                savedRoute.getId(),
                savedRoute.getRoute(),
                savedRoute.getPrice()
        );
    }

    public String deleteCommuteRoute(Integer userId, Integer id) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));

        for (Commute route : user.getCommutes()) {
            if (Objects.equals(route.getId(), id)) {
                commuteRepository.deleteById(id);
                return "Successfully Deleted";
            }
        }

        return "Id Not Found";
    }
}