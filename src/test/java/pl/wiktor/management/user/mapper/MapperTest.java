package pl.wiktor.management.user.mapper;

import org.junit.Test;
import org.junit.internal.runners.JUnit38ClassRunner;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.wiktor.management.user.model.dto.request.AccountDTO;
import pl.wiktor.management.user.model.entity.Account;
import pl.wiktor.management.user.model.mapper.Mapper;

import static org.junit.Assert.assertEquals;

@RunWith(BlockJUnit4ClassRunner.class)
public class MapperTest {
    private Mapper mapper;

    @Test
    public void map(){
        Account expected = new Account();
        expected.setId(0);
        expected.setLogin("wiktor");
        expected.setPassword("wiktorhaslo");

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(0);
        accountDTO.setLogin("wiktor");
        accountDTO.setPassword("wiktorhaslo");

        Account actual = Mapper.map(accountDTO);
        assertEquals(expected.getId(),actual.getId());
        assertEquals(expected.getLogin(),actual.getLogin());
        assertEquals(expected.getPassword(),actual.getPassword());
        assertEquals(expected, actual);
    }
}
