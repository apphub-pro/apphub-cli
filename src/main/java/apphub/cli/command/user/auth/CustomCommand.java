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

package apphub.cli.command.user.auth;

import apphub.util.Command;
import apphub.util.CommandException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClient;
import com.amazonaws.services.identitymanagement.model.GetGroupRequest;
import com.amazonaws.services.identitymanagement.model.User;

import java.util.List;
import java.util.Map;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class CustomCommand extends Command {
    private static final long serialVersionUID = 1L;

    public CustomCommand(List<String> arguments, Map<String, String> parameters) {
        super(arguments, parameters);
    }

    @Override
    public void run() {
        String user = arguments.get(0);
        String secret = arguments.get(1);
        String[] creds = secret.split(":");
        AmazonIdentityManagement iam = new AmazonIdentityManagementClient(new BasicAWSCredentials(creds[0], creds[1]));
        User u = iam.getUser().getUser();
        if (!u.getUserName().equals(user)) {
            throw new CommandException(4);
        }
        List<User> gus = iam.getGroup(new GetGroupRequest("sportwise-dev")).getUsers();
        for (User gu : gus) {
            if (gu.getUserName().equals(user)) {
                return;
            }
        }
        throw new CommandException(4);
    }
}
