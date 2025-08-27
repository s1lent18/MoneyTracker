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

    public AddCommuteResponse addCommuteRoute(Integer userId, AddCommuteRequest request) {

        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));

        Commute route = new Commute(request.route, request.price, user);

        Commute savedRoute = commuteRepository.save(route);

        return new AddCommuteResponse(
                savedRoute.getId(),
                savedRoute.getRoute(),
                savedRoute.getPrice()
        );
    }
}
