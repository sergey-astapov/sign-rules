package io.signrools.fsm;

import io.signrools.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.qatools.fsm.StopConditionAware;
import ru.yandex.qatools.fsm.annotations.FSM;
import ru.yandex.qatools.fsm.annotations.OnTransit;
import ru.yandex.qatools.fsm.annotations.Transit;
import ru.yandex.qatools.fsm.annotations.Transitions;

@FSM(start = Idle.class)
@Transitions({
        @Transit(from = Idle.class, on = DocUploaded.class, to = Uploaded.class),
        @Transit(from = Uploaded.class, on = SignRuleProvided.class, to = Signing.class),
        @Transit(from = Signing.class, on = SignRuleUpdated.class, to = Signing.class),
        @Transit(from = Signing.class, on = SignRuleCompleted.class, to = Signing.class),
        @Transit(from = Signing.class, on = DocSigned.class, to = Signed.class),
})
public class ContextFsm implements StopConditionAware<ContextState, Event> {
    private static Logger log = LoggerFactory.getLogger(ContextFsm.class);

    private Context context;

    public ContextFsm(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    @OnTransit
    public void onDocUploaded(Idle from, Uploaded to, DocUploaded event) {
        context.state().validate(event);
        updateContext(context.update(Uploaded.apply()));
    }

    @OnTransit
    public void onSignRuleProvided(Uploaded from, Signing to, SignRuleProvided event) {
        context.state().validate(event);
        updateContext(context.update(Signing.apply()));
    }

    @OnTransit
    public void onSignRuleUpdated(Signing from, Signing to, SignRuleUpdated event) {
        context.state().validate(event);
        updateContext(context.update(Signing.apply()));
    }

    @OnTransit
    public void onSignRuleCompleted(Signing from, Signing to, SignRuleCompleted event) {
        context.state().validate(event);
        updateContext(context.update(Signing.apply()));
    }

    @OnTransit
    public void onSignRuleCompleted(Signing from, Signed to, DocSigned event) {
        context.state().validate(event);
        updateContext(context.update(Signed.apply()));
    }

    @Override
    public boolean isStopRequired(ContextState state, Event event) {
        return false;//this.context.isAllProcessed();
    }

    private void updateContext(Context newContext) {
        log.info("updateContext, oldContext: {}, newContext: {}", context, newContext);
        context = newContext;
    }

    private void validate(Event event) {
        //TODO
    }
}
