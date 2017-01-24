/*
 * Copyright (C) 2014 Dmitry Kotlyarov.
 * All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package apphub.cli;

import apphub.cli.command.CustomCommand;
import apphub.util.Command;
import apphub.util.CommandException;
import apphub.util.ParameterCommandException;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        try {
            System.out.println("");
            System.out.println("");
            List<String> arguments = new LinkedList<>();
            Map<String, String> parameters = new LinkedHashMap<>(args.length);
            boolean f = true;
            for (String a : args) {
                if (f) {
                    if (a.startsWith("--")) {
                        f = false;
                    } else {
                        arguments.add(a);
                    }
                }
                if (!f) {
                    if (a.startsWith("--")) {
                        String anp = a.substring(2);
                        int i = anp.indexOf("=");
                        if (i == -1) {
                            parameters.put(anp, null);
                        } else {
                            parameters.put(anp.substring(0, i), anp.substring(i + 1));
                        }
                    } else {
                        throw new ParameterCommandException(String.format("Parameter '%s' does not start with prefix '--'", a));
                    }
                }
            }
            Command c = new CustomCommand(arguments, parameters);
            c.run();
        } catch (CommandException e) {
            System.out.println(String.format("[ERROR]: %s", e.getMessage()));
            System.exit(e.getStatus());
        } catch (Throwable e) {
            System.out.println(String.format("[ERROR]: %s", e.getMessage()));
            System.exit(1);
        }
    }
}
