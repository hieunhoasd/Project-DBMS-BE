package back_end_Group_13.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import back_end_Group_13.Domain.Trans;
import back_end_Group_13.Repository.TransRepository;

@Service
public class TransService {
    private final TransRepository transRepository;

    public TransService(TransRepository transRepository) {
        this.transRepository = transRepository;
    }

}
