package back_end_Group_13.Controller;

import org.springframework.web.bind.annotation.RestController;

import back_end_Group_13.Domain.Trans;
import back_end_Group_13.Domain.User;
import back_end_Group_13.Repository.TransRepository;
import back_end_Group_13.Repository.UserRepository;
import back_end_Group_13.Service.TransService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class TransController {
    private final TransService transService;
    private final TransRepository transRepository;
    private final UserRepository userRepository;

    public TransController(TransService transService, UserRepository userRepository, TransRepository transRepository) {
        this.transService = transService;
        this.userRepository = userRepository;
        this.transRepository = transRepository;
    }

    @GetMapping("/transactions/{userId}")
    public ResponseEntity<?> getTransactionHistory(@PathVariable Integer userId) {
        List<Trans> transactions = transRepository
                .findBySenderIdOrReceiverIdOrderByTransactionDateDesc(userId, userId);

        List<Map<String, Object>> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        for (Trans t : transactions) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", t.getId());
            map.put("amount", t.getAmount());
            map.put("content", t.getContent());
            map.put("date", t.getTransactionDate() != null ? sdf.format(t.getTransactionDate()) : "");

            if (t.getSenderId().equals(userId)) {
                map.put("type", "send");
                User receiver = userRepository.findById(Long.valueOf(t.getReceiverId())).orElse(null);
                map.put("name",
                        receiver != null && receiver.getFullname() != null ? receiver.getFullname() : "Customer");
            }

            else {
                map.put("type", "receive");
                User sender = userRepository.findById(Long.valueOf(t.getSenderId())).orElse(null);
                map.put("name", sender != null && sender.getFullname() != null ? sender.getFullname() : "Customer");
            }

            result.add(map);
        }

        return ResponseEntity.ok(result);
    }

}
