package com.shaohua.sheducation.common;

import com.shaohua.sheducation.request.AppSettingBody;
import com.shaohua.sheducation.request.BuyCoinBody;
import com.shaohua.sheducation.request.ChangePassBody;
import com.shaohua.sheducation.request.EditMyInfoBody;
import com.shaohua.sheducation.request.FriendSettingBody;
import com.shaohua.sheducation.request.LabelCustomBody;
import com.shaohua.sheducation.request.LoginBody;
import com.shaohua.sheducation.request.MilestoneAddBody;
import com.shaohua.sheducation.request.MilestoneAlarmBody;
import com.shaohua.sheducation.request.MilestoneClockBody;
import com.shaohua.sheducation.request.MilestoneRemindBody;
import com.shaohua.sheducation.request.MyMessageReadBody;
import com.shaohua.sheducation.request.PlanAcceptBody;
import com.shaohua.sheducation.request.PlanCreateBody;
import com.shaohua.sheducation.request.PlanInviteBody;
import com.shaohua.sheducation.request.PlanJoinBody;
import com.shaohua.sheducation.request.PlanPayBody;
import com.shaohua.sheducation.request.RechargeBody;
import com.shaohua.sheducation.request.RegisterBody;
import com.shaohua.sheducation.request.ResetPassBody;
import com.shaohua.sheducation.request.SMSRegisterBody;
import com.shaohua.sheducation.request.StudySubscribeBody;
import com.shaohua.sheducation.request.ValidCodeBody;
import com.shaohua.sheducation.resultbean.AppSettingResultVO;
import com.shaohua.sheducation.resultbean.BuyCoinResultVO;
import com.shaohua.sheducation.resultbean.CommonResultVO;
import com.shaohua.sheducation.resultbean.EditMyInfoResultVO;
import com.shaohua.sheducation.resultbean.FriendAcceptReultVO;
import com.shaohua.sheducation.resultbean.FriendInviteResultVO;
import com.shaohua.sheducation.resultbean.FriendSettingResultVO;
import com.shaohua.sheducation.resultbean.GetBannersResultVO;
import com.shaohua.sheducation.resultbean.GetFriendMyResultVO;
import com.shaohua.sheducation.resultbean.GetFriendNewResultVO;
import com.shaohua.sheducation.resultbean.GetInformationResultVO;
import com.shaohua.sheducation.resultbean.GetMilestonesConfirmResultVO;
import com.shaohua.sheducation.resultbean.GetMilestonesResultVO;
import com.shaohua.sheducation.resultbean.GetMyInfoResultVO;
import com.shaohua.sheducation.resultbean.GetMyMessageResultVO;
import com.shaohua.sheducation.resultbean.GetMyWalletResultVO;
import com.shaohua.sheducation.resultbean.GetPlanInfoResultVO;
import com.shaohua.sheducation.resultbean.GetPlanInvitedResultVO;
import com.shaohua.sheducation.resultbean.GetPlanListResultVO;
import com.shaohua.sheducation.resultbean.GetPlanMatchResultVO;
import com.shaohua.sheducation.resultbean.GetPlanMyResultVO;
import com.shaohua.sheducation.resultbean.GetPlanRecommendResultVO;
import com.shaohua.sheducation.resultbean.GetStudyFavoriteListResultVO;
import com.shaohua.sheducation.resultbean.GetStudyListResultVO;
import com.shaohua.sheducation.resultbean.GetStudyMeListResultVO;
import com.shaohua.sheducation.resultbean.GetStudyRecommendListResultVO;
import com.shaohua.sheducation.resultbean.GetStudySmartResultVO;
import com.shaohua.sheducation.resultbean.GetSystemLabelsResultVO;
import com.shaohua.sheducation.resultbean.GetUserIdsResultVO;
import com.shaohua.sheducation.resultbean.GetUserInfoVO;
import com.shaohua.sheducation.resultbean.GetUserListResultVO;
import com.shaohua.sheducation.resultbean.GetUserPlansVO;
import com.shaohua.sheducation.resultbean.GetUserRecommendResultVO;
import com.shaohua.sheducation.resultbean.LabelCancelResultVO;
import com.shaohua.sheducation.resultbean.LabelCustomResultVO;
import com.shaohua.sheducation.resultbean.LabelSelectResultVO;
import com.shaohua.sheducation.resultbean.LoginResultVO;
import com.shaohua.sheducation.resultbean.MilestoneAddResultVO;
import com.shaohua.sheducation.resultbean.MilestoneAlarmVO;
import com.shaohua.sheducation.resultbean.MilestoneClockVO;
import com.shaohua.sheducation.resultbean.MilestoneDeleteVO;
import com.shaohua.sheducation.resultbean.MilestoneEditResultVO;
import com.shaohua.sheducation.resultbean.MilestoneRemindVO;
import com.shaohua.sheducation.resultbean.MilestonesRefuseResultVO;
import com.shaohua.sheducation.resultbean.MilestonesSubmitResultVO;
import com.shaohua.sheducation.resultbean.MyMessageReadVO;
import com.shaohua.sheducation.resultbean.PlanAcceptVO;
import com.shaohua.sheducation.resultbean.PlanCreateResultVO;
import com.shaohua.sheducation.resultbean.PlanExitVO;
import com.shaohua.sheducation.resultbean.PlanFinishVO;
import com.shaohua.sheducation.resultbean.PlanInviteResultVO;
import com.shaohua.sheducation.resultbean.PlanJoinVO;
import com.shaohua.sheducation.resultbean.PlanOverVO;
import com.shaohua.sheducation.resultbean.PlanPayVO;
import com.shaohua.sheducation.resultbean.PlanRefuseVO;
import com.shaohua.sheducation.resultbean.RechargeResultVO;
import com.shaohua.sheducation.resultbean.RegisterResultVO;
import com.shaohua.sheducation.resultbean.ResetPassResultVO;
import com.shaohua.sheducation.resultbean.SMSForgetResultVO;
import com.shaohua.sheducation.resultbean.SMSRegisterResultVO;
import com.shaohua.sheducation.resultbean.StudyCollectCancelResultVO;
import com.shaohua.sheducation.resultbean.StudyCollectResultVO;
import com.shaohua.sheducation.resultbean.StudyLikeResultVO;
import com.shaohua.sheducation.resultbean.StudySubscribeResultVO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by chuan on 2017/11/24.
 */

public interface ShHttpService {

    @POST(ConstsUrl.SMSRegister)
    Call<SMSRegisterResultVO> SMSRegister(@Body SMSRegisterBody body);

    @POST(ConstsUrl.SMSForget)
    Call<SMSForgetResultVO> SMSForget(@Body SMSRegisterBody body);

    @POST(ConstsUrl.ValidCode)
    Call<CommonResultVO> ValidCode(@Body ValidCodeBody body);

    @POST(ConstsUrl.ResetPass)
    Call<ResetPassResultVO> ResetPass(@Body ResetPassBody body);


    @POST(ConstsUrl.ChangePass)
    Call<CommonResultVO> ChangePass(@Body ChangePassBody body);


    @POST(ConstsUrl.Register)
    Call<RegisterResultVO> Register(@Body RegisterBody body);

    @POST(ConstsUrl.Login)
    Call<LoginResultVO> Login(@Body LoginBody body);

    @POST(ConstsUrl.EditMyInfo)
    Call<EditMyInfoResultVO> EditMyInfo(@Body EditMyInfoBody body);

    @POST(ConstsUrl.AppSetting)
    Call<AppSettingResultVO> AppSetting(@Body AppSettingBody body);

    @POST(ConstsUrl.LabelCustom)
    Call<LabelCustomResultVO> LabelCustom(@Body LabelCustomBody body);

    @POST(ConstsUrl.FriendSetting)
    Call<FriendSettingResultVO> FriendSetting(@Body FriendSettingBody body);

    @POST(ConstsUrl.MilestoneAdd)
    Call<MilestoneAddResultVO> MilestoneAdd(@Body MilestoneAddBody body);

    @POST(ConstsUrl.MilestoneClock)
    Call<MilestoneClockVO> MilestoneClock(@Path("id") int id, @Body MilestoneClockBody body);

    @POST(ConstsUrl.MilestoneRemind)
    Call<MilestoneRemindVO> MilestoneRemind(@Path("id") int id, @Body MilestoneRemindBody body);

    @POST(ConstsUrl.MilestoneAlarm)
    Call<MilestoneAlarmVO> MilestoneAlarm(@Path("id") int id, @Body MilestoneAlarmBody body);

    @DELETE(ConstsUrl.MilestoneDelete)
    Call<MilestoneDeleteVO> MilestoneDelete(@Path("id") int id);

    @PUT(ConstsUrl.MilestoneEdit)
    Call<MilestoneEditResultVO> MilestoneEdit(@Body MilestoneAddBody body);

    @POST(ConstsUrl.PlanCreate)
    Call<PlanCreateResultVO> PlanCreate(@Body PlanCreateBody body);

    @POST(ConstsUrl.StudySubscribe)
    Call<StudySubscribeResultVO> StudySubscribe(@Body StudySubscribeBody body);

    @POST(ConstsUrl.StudyLike)
    Call<StudyLikeResultVO> StudyLike(@Path("id") int id);

    @POST(ConstsUrl.StudyCollect)
    Call<StudyCollectResultVO> StudyCollect(@Path("id") int id);

    @POST(ConstsUrl.StudyCollectCancel)
    Call<StudyCollectCancelResultVO> StudyCollectCancel(@Path("id") int id);

    @POST(ConstsUrl.LabelSelect)
    Call<LabelSelectResultVO> LabelSelect(@Path("id") int id);

    @POST(ConstsUrl.LabelCancel)
    Call<LabelCancelResultVO> LabelCancel(@Path("id") int id);

    @POST(ConstsUrl.PlanInvite)
    Call<PlanInviteResultVO> PlanInvite(@Path("id") int id, @Body PlanInviteBody body);

    @POST(ConstsUrl.PlanJoin)
    Call<PlanJoinVO> PlanJoin(@Path("id") int id, @Body PlanJoinBody body);

    @POST(ConstsUrl.PlanExit)
    Call<PlanExitVO> PlanExit(@Path("id") int id);

    @POST(ConstsUrl.PlanFinish)
    Call<PlanFinishVO> PlanFinish(@Path("id") int id);

    @POST(ConstsUrl.PlanOver)
    Call<PlanOverVO> PlanOver(@Path("id") int id);

    @POST(ConstsUrl.PlanAccept)
    Call<PlanAcceptVO> PlanAccept(@Path("id") int id, @Body PlanAcceptBody body);

    @POST(ConstsUrl.PlanRefuse)
    Call<PlanRefuseVO> PlanRefuse(@Path("id") int id);

    @POST(ConstsUrl.PlanPay)
    Call<PlanPayVO> PlanPay(@Path("id") int id, @Body PlanPayBody body);

    @POST(ConstsUrl.GetMilestonesConfirm)
    Call<GetMilestonesConfirmResultVO> GetMilestonesConfirm(@Path("id") int id);

    @POST(ConstsUrl.MilestonesRefuse)
    Call<MilestonesRefuseResultVO> MilestonesRefuse(@Path("id") int id);

    @POST(ConstsUrl.MilestonesSubmit)
    Call<MilestonesSubmitResultVO> MilestonesSubmit(@Path("id") int id);

    @POST(ConstsUrl.FriendInvite)
    Call<FriendInviteResultVO> FriendInvite(@Path("id") int id);

    @POST(ConstsUrl.FriendAccept)
    Call<FriendAcceptReultVO> FriendAccept(@Path("id") int id);

    @POST(ConstsUrl.BuyCoin)
    Call<BuyCoinResultVO> BuyCoin(@Body BuyCoinBody body);

    @POST(ConstsUrl.Recharge)
    Call<RechargeResultVO> Recharge(@Body RechargeBody body);

    @POST(ConstsUrl.MyMessageRead)
    Call<MyMessageReadVO> MyMessageRead(@Body MyMessageReadBody body);


    @GET(ConstsUrl.GetSystemLabels)
    Call<GetSystemLabelsResultVO> GetSystemLabels();

    @GET(ConstsUrl.GetMyLabels)
    Call<GetSystemLabelsResultVO> GetMyLabels();


    @GET(ConstsUrl.GetBanners)
    Call<GetBannersResultVO> GetBanners();


    @GET(ConstsUrl.GetInformation)
    Call<GetInformationResultVO> GetInformation(@Query("labels") String labels);

    @GET(ConstsUrl.GetMyInfo)
    Call<GetMyInfoResultVO> GetMyInfo();

    @GET(ConstsUrl.GetMyWallet)
    Call<GetMyWalletResultVO> GetMyWallet();

    @GET(ConstsUrl.GetPlanInfo)
    Call<GetPlanInfoResultVO> GetPlanInfo(@Path("id") int id);

    @GET(ConstsUrl.GetUserInfo)
    Call<GetUserInfoVO> GetUserInfo(@Path("id") int id);

    @GET(ConstsUrl.GetUserPlans)
    Call<GetUserPlansVO> GetUserPlans(@Path("id") int id, @Query("page") int page, @Query("pageSize") int pageSize);

    @GET(ConstsUrl.GetUserIds)
    Call<GetUserIdsResultVO> GetUserIds(@Query("users") String users);

    @GET(ConstsUrl.GetMilestones)
    Call<GetMilestonesResultVO> GetMilestones(@Path("id") int id);

    @GET(ConstsUrl.GetPlanMy)
    Call<GetPlanMyResultVO> GetPlanMy(@Query("status") int status, @Query("page") int page, @Query("pageSize") int pageSize);

    @GET(ConstsUrl.GetPlanMatch)
    Call<GetPlanMatchResultVO> GetPlanMatch(@Path("id") int id, @Query("page") int page, @Query("pageSize") int pageSize);

    @GET(ConstsUrl.GetMyMessage)
    Call<GetMyMessageResultVO> GetMyMessage(@Query("status") int status, @Query("page") int page, @Query("pageSize") int pageSize);

    @GET(ConstsUrl.GetFriendMy)
    Call<GetFriendMyResultVO> GetFriendMy(@Query("page") int page, @Query("pageSize") int pageSize);

    @GET(ConstsUrl.GetFriendNew)
    Call<GetFriendNewResultVO> GetFriendNew(@Query("page") int page, @Query("pageSize") int pageSize);

    @GET(ConstsUrl.GetPlanRecommend)
    Call<GetPlanRecommendResultVO> GetPlanRecommend(@Query("labels") String labels, @Query("page") int page,
                                                    @Query("pageSize") int pageSize);


    @GET(ConstsUrl.GetUserList)
    Call<GetUserListResultVO> GetUserList(@Query("labels") String labels, @Query("distance") String distance,
                                          @Query("education") int education, @Query("gender") int gender,
                                          @Query("age") int age, @Query("page") int page,
                                          @Query("pageSize") int pageSize);

    @GET(ConstsUrl.GetUserRecommend)
    Call<GetUserRecommendResultVO> GetUserRecommend(@Query("labels") String labels);

    @GET(ConstsUrl.GetPlanList)
    Call<GetPlanListResultVO> GetPlanList(@Query("labels") String labels, @Query("q") String q,
                                          @Query("planMode") int planMode, @Query("depositMode") int depositMode,
                                          @Query("status") int status, @Query("page") int page,
                                          @Query("pageSize") int pageSize);

    @GET(ConstsUrl.GetPlanInvited)
    Call<GetPlanInvitedResultVO> GetPlanInvited(@Query("page") int page,
                                                @Query("pageSize") int pageSize);

    @GET(ConstsUrl.GetStudyList)
    Call<GetStudyListResultVO> GetStudyList(@Query("labels") String labels, @Query("q") String q, @Query("page") int page,
                                            @Query("pageSize") int pageSize);

    @GET(ConstsUrl.GetStudyRecommendList)
    Call<GetStudyRecommendListResultVO> GetStudyRecommendList(@Query("labels") String labels, @Query("q") String q, @Query("page") int page,
                                                              @Query("pageSize") int pageSize);

    @GET(ConstsUrl.GetStudySmart)
    Call<GetStudySmartResultVO> GetStudySmart(@Query("labels") String labels, @Query("q") String q, @Query("page") int page,
                                              @Query("pageSize") int pageSize);

    @GET(ConstsUrl.GetStudyMeList)
    Call<GetStudyMeListResultVO> GetStudyMeList(@Query("labels") String labels, @Query("q") String q, @Query("page") int page,
                                                @Query("pageSize") int pageSize);

    @GET(ConstsUrl.GetStudyFavoriteList)
    Call<GetStudyFavoriteListResultVO> GetStudyFavoriteList(@Query("labels") String labels, @Query("q") String q, @Query("page") int page,
                                                            @Query("pageSize") int pageSize);


}
