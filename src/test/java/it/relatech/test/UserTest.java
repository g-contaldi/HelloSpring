package it.relatech.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.relatech.config.AppConfig;
import it.relatech.config.HibernateConfiguration;
import it.relatech.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, HibernateConfiguration.class })
@WebAppConfiguration
@Transactional
public class UserTest {

	@Autowired
	private WebApplicationContext ctx;

	private MockMvc mockMvc;

	private ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}

	/**
	 * Test for persist on db. At the end of test the transaction is rollabacked and the record is cancelled from the db.
	 * 
	 * @throws Exception
	 */
	@Test
	public void saveUser() throws Exception {
		User user = new User();
		user.setUsername("pippo");
		user.setPassword("123");

		mockMvc.perform(
				post("/user/save").content(mapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isCreated());
	}

	/**
	 * Test for select by id. Al least one record in db must be present.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetById() throws Exception {
		int id = 1;
		mockMvc.perform(get("/user/getById/" + id)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andDo(print()).andReturn().getResponse();
	}

	/**
	 * Test for select *. The control is done on the first and second records, that must be, on username field, "giacomino" and "mimmo".

	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetList() throws Exception {
		mockMvc.perform(get("/user/getList")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andDo(print())
				.andExpect(jsonPath("$[0].username").value("giacomino"))
				.andExpect(jsonPath("$[1].username").value("mimmo"));
	}

	/**
	 * Test with where clause. On db must be present a record with -> username: giacomino and password: 123456.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetUserByUsernameAndPassword() throws Exception {
		mockMvc.perform(
				get("/user/getByUsernameAndPassword").header("username", "giacomino").header("password", "123456"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print()).andExpect(jsonPath("$.username").value("giacomino"));

	}

}
