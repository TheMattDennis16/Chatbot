import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import types.StringPair;

import static com.google.common.base.Preconditions.checkNotNull;

public class CLIParsing {
    private static final StringPair USE_STREAMING = new StringPair("s", "Use always-on voice streaming.");
    private static final Logger LOG = LoggerFactory.getLogger(ALIE.class);

    private String[] args;

    public CLIParsing(String[] args) {
        this.args = checkNotNull(args);
    }

    private Options getOptions() {
        Options options = new Options();
        options.addOption(USE_STREAMING.left, false, USE_STREAMING.right);

        return options;
    }

    public void parseOptions() {
        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cli = parser.parse(getOptions(), args);
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
    }

    public class Settings {

    }
}
