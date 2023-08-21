package todosimple.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todosimple.models.User;
import todosimple.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User findByID(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException(
                "Usuário não encontrado! Id: " + id + ", Tipo: " + User.class.getName()));
    }

    @Transactional
    public User create(User obj){

        obj.setId(null);
        obj = this.userRepository.save(obj);
        return obj;
}


    @Transactional
    public User update(User obj){
        User newObj = findByID(obj.getId());
        newObj.setPassword(obj.getPassword());
        return this.userRepository.save(newObj);
    }

    public void delete(Long id){
        findByID(id);
        try{
            this.userRepository.deleteById(id);
        }
        catch (Exception e){
            throw new RuntimeException("não foi possível excluir pois há entidades relacionadas!" + e);
        }
    }
}
