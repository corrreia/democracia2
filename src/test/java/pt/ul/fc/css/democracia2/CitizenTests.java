/*
 * @author: Tomás Correia fc56372
 * @author: Miguel Pato fc57102
 * @author: João Vieira fc45677
 */
package pt.ul.fc.css.democracia2;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import pt.ul.fc.css.democracia2.services.CitizenService;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CitizenTests {

    @Autowired
    private CitizenService citizenService;

    @Test
    public void testRegisterCitizen() {
        // Register a citizen with a unique ID
        citizenService.registerCitizen("Correia", "Tomás", "627345265");

        // Try to register a citizen with the same ID, should throw an exception
        assertThrows(IllegalArgumentException.class,
                () -> citizenService.registerCitizen("Pato", "Miguel", "627345265"));
        
    }

    @Test
    public void testRegisterCitizenWithNullID() {
        // Try to register a citizen with a null ID, should throw an exception
        assertThrows(IllegalArgumentException.class,
                () -> citizenService.registerCitizen("Pato", "Miguel", null));
    }

    @Test
    public void testRegisterCitizenWithEmptyID() {
        // Try to register a citizen with an empty ID, should throw an exception
        assertThrows(IllegalArgumentException.class,
                () -> citizenService.registerCitizen("Pato", "Miguel", ""));
    }

    @Test
    public void testRegisterCitizenWithNullFirstName() {
        // Try to register a citizen with a null first name, should throw an exception
        assertThrows(IllegalArgumentException.class,
                () -> citizenService.registerCitizen(null, "Miguel", "627345265"));
    }

}