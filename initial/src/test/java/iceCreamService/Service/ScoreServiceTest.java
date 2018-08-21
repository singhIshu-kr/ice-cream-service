package iceCreamService.Service;

import iceCreamService.Domain.Score;
import iceCreamService.Exception.InvalidMemberOrTeamId;
import iceCreamService.Repository.ScoreRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ScoreServiceTest {

    @Mock
    private ScoreRepository scoreRepository;

    @Mock
    private MemberService memberService;

    private ScoreService scoreService;

    @Before
    public void setUp() throws Exception {
        scoreService = new ScoreService(scoreRepository,memberService);
    }

    @Test
    public void shouldAddAnEntryIfMemberAndTeamAreValid() throws InvalidMemberOrTeamId {
        Score score = new Score("20976", "123", new Date(), false);
        when(memberService.isTeamIDAndMemberIdMatch(score.memberId,score.teamId)).thenReturn(true);
        scoreService.addScore(score);
        verify(scoreRepository,times(1)).save(score);
    }

    @Test
    public void shouldReturnScoreOfAMember() {
        Score score = new Score("20976", "123", new Date(), false);
        scoreService.getScore("20976");
        verify(scoreRepository,times(1)).findAllByMemberId("20976");
        when(scoreRepository.findAllByMemberId("20976")).thenReturn(asList(score));
        assertEquals(scoreService.getScore("20976"),1);
    }

    @Test
    public void shouldReturnZeroIfMemberEntryIsNotThereOrIsRedeemed() {
        Score score = new Score("20976", "123", new Date(), true);
        scoreService.getScore("20976");
        verify(scoreRepository,times(1)).findAllByMemberId("20976");
        when(scoreRepository.findAllByMemberId("20976")).thenReturn(asList(score));
        assertEquals(scoreService.getScore("20976"),0);
    }

    @Test
    public void shouldResetScoreOfTheMember() {
        Score score = new Score("20976", "123", new Date(), false);
        scoreService.resetScore("20976");
        verify(scoreRepository,times(1)).findAllByMemberId("20976");
        when(scoreRepository.findAllByMemberId("20976")).thenReturn(asList(score));
    }
}
