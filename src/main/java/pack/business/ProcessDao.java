package pack.business;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import pack.mybatis.SqlMapConfig;

public class ProcessDao {
	private SqlSessionFactory sqlSessionFactory = SqlMapConfig.getSqlSession();

	public List<DataDto> selectMemberAll() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<DataDto> list = null;
		try {
			list = sqlSession.selectList("selectDataAll");
		} catch (Exception e) {
			System.out.println("selectMemberAll err : " + e);
		} finally {
			if (sqlSession != null)
				sqlSession.close();
		}

		return list;
	}
	
	public boolean insertMember(DataFormBean bean) {
		boolean b = false;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			if(sqlSession.insert("insertData", bean) > 0) b = true;
			sqlSession.commit();
		} catch (Exception e) {
			System.out.println("insertMember err: " + e);
		} finally {
			if(sqlSession != null) sqlSession.close();
		}
		return b;
	}
	
	public DataDto selectMember(String id) {
		DataDto dto = null;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			dto = sqlSession.selectOne("selectDataPart", id);
		} catch (Exception e) {
			System.out.println("selectMember err: " + e);
		} finally {
			if(sqlSession != null) sqlSession.close();
		}
		return dto;
	}
	
	public boolean updateMember(DataFormBean bean) {
		boolean b = false;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			//비번 비교 후 update 결정
			DataDto dto = selectMember(bean.getId()); //사용자가 입력한 건 bean에 있고 dto에 있는 건 db에 있다.
			
			if(dto.getPasswd().equals(bean.getPasswd())) {
				if(sqlSession.update("updateData", bean) > 0 ) b = true; 
				sqlSession.commit();
			}
		} catch (Exception e) {
			System.out.println("updateMember err: " + e);
			sqlSession.rollback();
		} finally {
			if (sqlSession != null) sqlSession.close();
		}
		return b;
	}
	
	public boolean deleteMember(String id) {
		boolean b = false;
		SqlSession sqlSession = sqlSessionFactory.openSession(); //현재 수동임
		try {
			int count = sqlSession.delete("deleteData", id);
			if (count > 0) {
				b = true;
				sqlSession.commit();
			} else {
				sqlSession.rollback();	
			}
			sqlSession.commit();
		} catch (Exception e) {
			System.out.println("deleteMember err: " + e);
			sqlSession.rollback();
		} finally {
			if (sqlSession != null) sqlSession.close();
		}
		return b;
	}
}
