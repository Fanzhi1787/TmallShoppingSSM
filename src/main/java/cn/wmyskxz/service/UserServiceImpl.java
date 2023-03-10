package cn.wmyskxz.service;

import cn.wmyskxz.mapper.UserMapper;
import cn.wmyskxz.pojo.User;
import cn.wmyskxz.pojo.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserService 实现类
 *
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;


	public List<User> list() {
		UserExample example = new UserExample();
		return userMapper.selectByExample(example);
	}


	public void updatePassword(int id, String password) {
		User user = get(id);
		user.setPassword(password);
		userMapper.updateByPrimaryKey(user);
	}


	public User get(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}


	public User get(String name, String password) {
		UserExample example = new UserExample();
		example.or().andNameEqualTo(name).andPasswordEqualTo(password);
		List<User> result = userMapper.selectByExample(example);
		if (result.isEmpty())
			return null;
		return result.get(0);
	}


	public boolean isExist(String name) {
		UserExample example =new UserExample();
		example.or().andNameEqualTo(name);
		List<User> result= userMapper.selectByExample(example);
		if(!result.isEmpty())
			return true;
		return false;
	}


	public void add(User user) {
		userMapper.insert(user);
	}
}
