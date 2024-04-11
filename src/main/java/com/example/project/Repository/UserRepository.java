package com.example.project.Repository;

import com.example.project.Model.User;
import com.example.project.Repository.Projection.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
   // boolean existsByUserEmail(String userEmail);

    boolean existsByUserMobileNo(String userMobileNo);


    @Query(value ="select * from `project_User` where user_is_deleted=false ",nativeQuery = true)
    List<User> getAllUser();

    @Query(value = "select u.userId as userId,u.userName as userName,u.userEmail as userEmail from User as u where u.userName like %:userName% and u.userIsDeleted=false order By u.createdAt desc ")
    Page<UserProjection> getAllByUserName(String userName, Pageable pageable);

    @Query(value = "select u.userId as userId,u.userName as userName,u.userEmail as userEmail from User as u where u.userIsDeleted=false order by u.createdAt desc ",nativeQuery = false)
    Page<UserProjection> getAll(Pageable pageable);

    @Query(value = "select u.userId as userId,u.userName as userName,u.userEmail as userEmail from User as u where u.userEmail like %:userEmail% and u.userIsDeleted=false order by u.createdAt desc ")
    Page<UserProjection> getAllUserEmail(String userEmail, Pageable pageable);

    @Query(value = "select u.userId as userId,u.userName as userName,u.userEmail as userEmail from User as u where u.userMobileNo like %:userMobileNo% and u.userIsDeleted=false order by u.createdAt desc ")
    Page<UserProjection> getAllUserMobileNo(String userMobileNo, Pageable pageable);

    @Query(value = "select u.userId as userId,u.userName as userName,u.userEmail as userEmail from User as u where u.userName like %:userName% and u.userEmail like %:userEmail% and u.userIsDeleted=false order by u.createdAt desc ")
    Page<UserProjection> getAllByUserNameAndUserEmail(String userName, String userEmail, Pageable pageable);

    @Query(value = "select u.userId as userId,u.userName as userName,u.userEmail as userEmail from User as u where u.userName like %:userName% and u.userMobileNo like %:userMobileNo% and u.userIsDeleted=false order by u.createdAt desc ")
    Page<UserProjection> getAllUserNameAndUserMobileNo(String userName, String userMobileNo, Pageable pageable);

    @Query(value = "select u.userId as userId,u.userName as userName,u.userEmail as userEmail from User as u where u.userEmail like %:userEmail% and u.userMobileNo like %:userMobileNo% and u.userIsDeleted=false order by u.createdAt desc " )
    Page<UserProjection> getAllUserEmailAndUserMobileNo(String userEmail, String userMobileNo, Pageable pageable);

    @Query(value = "select u.userId as userId,u.userName as userName,u.userEmail as userEmail from User as u where u.userName like %:userName% and u.userEmail like %:userEmail% and u.userMobileNo like %:userMobileNo% and u.userIsDeleted=false order by u.createdAt desc ")
    Page<UserProjection> getAllByUserNameAndUserEmailAndUserMobileNo(String userName, String userEmail, String userMobileNo, Pageable pageable);
    @Modifying
    @Transactional
    @Query(value = "delete from `project_User` where user_id in (:userList)",nativeQuery = true)
    void deleteByIdIn(List<Long> userList);

    boolean existsByUserEmailAndUserIdNotIn(String userEmail, List<Long> userIds);

    boolean existsByUserEmail(String trim);

    boolean existsByUserMobileNoAndUserIdNotIn(String trim, List<Long> userIds);
}
