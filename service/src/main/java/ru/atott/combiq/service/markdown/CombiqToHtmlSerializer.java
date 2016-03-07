package ru.atott.combiq.service.markdown;

import org.pegdown.LinkRenderer;
import org.pegdown.ToHtmlSerializer;
import org.pegdown.VerbatimSerializer;
import org.pegdown.plugins.ToHtmlSerializerPlugin;
import ru.atott.combiq.service.site.UserContext;
import ru.atott.combiq.service.user.UserRoles;

import java.util.List;
import java.util.Map;

public class CombiqToHtmlSerializer extends ToHtmlSerializer {

    private UserContext uc;

    public CombiqToHtmlSerializer(UserContext uc, LinkRenderer linkRenderer, Map<String, VerbatimSerializer> verbatimSerializers,
                                  List<ToHtmlSerializerPlugin> plugins) {
        super(linkRenderer, verbatimSerializers, plugins);
        this.uc = uc;
    }

    @Override
    protected void printImageTag(LinkRenderer.Rendering rendering) {
        if (uc.hasAnyRole(UserRoles.sa, UserRoles.contenter)) {
            super.printImageTag(rendering);
        } else {
            // Nothing to do.
        }
    }
}
