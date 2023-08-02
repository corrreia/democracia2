/*
 * @author: Tomás Correia fc56372
 * @author: Miguel Pato fc57102
 * @author: João Vieira fc45677
 */
package pt.ul.fc.css.democracia2.services;

import java.util.List;

import org.springframework.stereotype.Service;

import pt.ul.fc.css.democracia2.entities.Delegate;
import pt.ul.fc.css.democracia2.repositories.DelegateRepository;

@Service
public class DelegateService {

    private final DelegateRepository delegateRepository;

    public DelegateService(DelegateRepository delegateRepository) {
        this.delegateRepository = delegateRepository;
    }

    /*
     * Returns the delegate with the given governmentId
     * 
     * @param governmentId the governmentId of the delegate
     * 
     * @return the delegate with the given governmentId
     */
    public Delegate findDelegateByGovernmentId(String governmentId) {
        Delegate delegate = delegateRepository.findByGovernmentId(governmentId);
        if (delegate == null)
            throw new IllegalArgumentException("Delegate not found");
        return delegate;
    }

    /*
     * Registers a new delegate
     * 
     * @param surname the surname of the delegate
     * 
     * @param name the name of the delegate
     * 
     * @param governmentId the governmentId of the delegate
     * 
     * @return the delegate that was registered
     */
    public Delegate registerDelegate(String surname, String name, String governmentId) {
        if (delegateRepository.findByGovernmentId(governmentId) != null)
            throw new IllegalArgumentException("Delegate already registered");

        if (surname == null || surname.isEmpty())
            throw new IllegalArgumentException("Surname cannot be empty");

        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Name cannot be empty");

        if (governmentId == null || governmentId.isEmpty())
            throw new IllegalArgumentException("Government ID cannot be empty");

        Delegate delegate = new Delegate(surname, name, governmentId);
        delegateRepository.save(delegate);
        return delegate;
    }

    /*
     * Returns all delegates
     * 
     * @return all delegates
     */
    public List<Delegate> findAll() {
        return delegateRepository.findAll();
    }

}
